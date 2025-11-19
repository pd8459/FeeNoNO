package com.example.feenono.realestate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentTradeRepository extends JpaRepository<ApartmentTrade, Integer> {

    List<ApartmentTrade> findTop5000ByLatitudeIsNull();

    List<ApartmentTrade> findByApartmentName(String apartmentName);



}
