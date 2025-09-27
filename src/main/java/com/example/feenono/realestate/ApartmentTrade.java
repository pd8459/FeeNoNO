package com.example.feenono.realestate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ApartmentTrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByPosition(position = 1)
    private String siguungu;

    @CsvBindByPosition(position = 5)
    private String apartmentName;

    @CsvBindByPosition(position = 6)
    private String exclusiveArea;

    @CsvBindByPosition(position = 7)
    private String dealYearMonth;

    @CsvBindByPosition(position = 8)
    private String dealDay;

    @CsvBindByPosition(position = 9)
    private String dealAmount;

    @CsvBindByPosition(position = 11)
    private String floor;

    @CsvBindByPosition(position = 14)
    private String buildYear;

    @CsvBindByPosition(position = 15)
    private String roadNameAddress;
}