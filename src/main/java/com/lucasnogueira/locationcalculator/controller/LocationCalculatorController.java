
package com.lucasnogueira.locationcalculator.controller;

import com.lucasnogueira.locationcalculator.facade.LocationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/location")
public class LocationCalculatorController {
    @Autowired
    private LocationFacade facade;

    public LocationCalculatorController(LocationFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/calc")
    public ResponseEntity calcLocations(@RequestParam String locations) {
        List<String> splitedLocations = List.of(locations.split(";"));

        try {
            return ResponseEntity.ok(facade.getDistancesBy(splitedLocations));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Falha na matriz de endereços: " +  ex.getMessage());
        }
    }

}
