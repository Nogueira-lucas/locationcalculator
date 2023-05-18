package model;

import com.google.maps.model.DistanceMatrix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RankedLocations {
    private List<DistanceMatrix> distanceMatrix;
    private CoupleDescription nearestLocations;
    private CoupleDescription farestLocations;
}
