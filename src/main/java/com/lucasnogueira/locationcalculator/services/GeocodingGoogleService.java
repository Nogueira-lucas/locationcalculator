package com.lucasnogueira.locationcalculator.services;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.stereotype.Component;

import com.google.maps.DistanceMatrixApi;

@Component
public class GeocodingGoogleService {

    private GeoApiContext context;
    private String apiKey = ${googleKey};

    public GeocodingGoogleService() {
        this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    public DistanceMatrix getDistanceFromLocations(String origin, String destiny) throws Exception {
        try {
            DistanceMatrixApiRequest api = DistanceMatrixApi.newRequest(context);
            return api.origins(origin)
                    .destinations(destiny)
                    .mode(TravelMode.DRIVING)
                    .language("pt-BR")
                    .await();

        } catch (ApiException apiEx) {
            System.err.println(apiEx.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
