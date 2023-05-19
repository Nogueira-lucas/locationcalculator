package com.lucasnogueira.locationcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoupleDescription {
    private String locateOne;
    private String locateTwo;
    private long distanceInMeters;
}
