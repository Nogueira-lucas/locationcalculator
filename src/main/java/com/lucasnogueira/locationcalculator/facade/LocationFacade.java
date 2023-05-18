package com.lucasnogueira.locationcalculator.facade;

import com.google.gson.Gson;
import com.lucasnogueira.locationcalculator.services.GeocodingGoogleService;
import lombok.Data;
import model.CoupleDescription;
import model.OrderEnum;
import model.RankedLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.model.DistanceMatrix;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
public class LocationFacade {

    @Autowired
    private GeocodingGoogleService service;

    private final static int FIRST = 0;

    public String getDistancesBy(List<String> locations) {
        List<DistanceMatrix> results = new ArrayList<DistanceMatrix>();

        for(int i = 0; i < locations.size()-1; i++) {

            for(int j = i; j < locations.size()-1; j++) {
                String origin = locations.get(j);
                String destiny = locations.get(++j);

                try {
                     results.add(service.getDistanceFromLocations(origin, destiny));
                } catch (Exception e) {
                    System.out.println("Falha ao buscar matriz de endereços!");
                }
            }
        }

        CoupleDescription nearestLocations = getTopOrderedCouple(results, OrderEnum.NEAREST);
        CoupleDescription farestLocations = getTopOrderedCouple(results, OrderEnum.FAREST);
        RankedLocations rankedLocations = new RankedLocations(results, nearestLocations, farestLocations);

        return new Gson().toJson(rankedLocations);
    }

    private List<CoupleDescription> getCouples(List<DistanceMatrix> distanceMatrix) {
        return distanceMatrix.stream().map(distance -> {
            String locateOne = distance.originAddresses[FIRST];
            String locateTwo = distance.destinationAddresses[FIRST];

            long distanceInMeters = distance.rows[FIRST].elements[FIRST].distance.inMeters;

            return new CoupleDescription(locateOne, locateTwo, distanceInMeters);

        }).collect(Collectors.toList());
    }

    public CoupleDescription getTopOrderedCouple(List<DistanceMatrix> distanceMatrix, OrderEnum order) {
        if(order.equals(OrderEnum.NEAREST)) {
            Optional<CoupleDescription> nearestCouple  = getCouples(distanceMatrix).stream()
                    .min(Comparator.comparing(CoupleDescription::getDistanceInMeters));

            if(nearestCouple.isPresent()) {
                return nearestCouple.get();
            } else {
                return getCouples(distanceMatrix).get(FIRST);
            }
        }

        if(order.equals(OrderEnum.FAREST)) {
            Optional<CoupleDescription> farestCouple  = getCouples(distanceMatrix).stream()
                    .max(Comparator.comparing(CoupleDescription::getDistanceInMeters));

            if(farestCouple.isPresent()) {
                return farestCouple.get();
            } else {
                return getCouples(distanceMatrix).get(FIRST);
            }
        }

        return null;
    }
}
