package com.example.feenono.realestate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ApartmentTrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // API에서 받아올 데이터 필드 (타입 수정)
    private String apartmentName;
    private double exclusiveArea;   // String -> double
    private int dealYearMonth;    // String -> int
    private int dealDay;          // String -> int
    private int dealAmount;       // ★★★ String -> int ★★★ (오류 해결)
    private int floor;            // String -> int
    private int buildYear;        // String -> int

    // 지오코딩을 위해 '지번주소'를 저장할 필드
    private String roadNameAddress; // (ApiDataService에서 "읍면동 + 지번"을 여기에 저장)

    // 지오코딩 결과로 채워질 필드
    private Double latitude;
    private Double longitude;

    // siguungu 필드 (ApiDataService에서 현재 사용 안 함, 필요하면 추가)
    // private String siguungu;
}