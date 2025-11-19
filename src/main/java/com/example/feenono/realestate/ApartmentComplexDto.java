package com.example.feenono.realestate;

import lombok.Data;

@Data
public class ApartmentComplexDto {
    private String apartmentName;
    private String roadNameAddress;
    private Double latitude;
    private Double longitude;
    private long transactionCount;
    private double averageDealAmount;

    public ApartmentComplexDto(ApartmentTrade trade, long count, double avgAmount) {
        this.apartmentName = trade.getApartmentName();
        this.roadNameAddress = trade.getRoadNameAddress();
        this.latitude = trade.getLatitude();
        this.longitude = trade.getLongitude();
        this.transactionCount = count;
        this.averageDealAmount = avgAmount;
    }
}