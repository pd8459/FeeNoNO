package com.example.feenono.realestate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApartmentTradeController {

    private final ApartmentTradeService apartmentTradeService;

    @GetMapping("/api/trades")
    public ResponseEntity<List<ApartmentComplexDto>> getAllComplexes() {
        List<ApartmentComplexDto> complexes = apartmentTradeService.findAllComplexes();
        return ResponseEntity.ok(complexes);
    }


    @GetMapping("/api/trades/search")
    public ResponseEntity<List<ApartmentTrade>> searchTradesByApartmentName(@RequestParam String aptName) {
        List<ApartmentTrade> trades = apartmentTradeService.findTradesByApartmentName(aptName);
        return ResponseEntity.ok(trades);
    }
}