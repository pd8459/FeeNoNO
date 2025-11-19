package com.example.feenono.realestate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApartmentTradeService {

    private final ApartmentTradeRepository apartmentTradeRepository;

    public List<ApartmentComplexDto> findAllComplexes() {
        // 1. DB에서 모든 거래 정보를 가져옵니다.
        List<ApartmentTrade> allTrades = apartmentTradeRepository.findAll();

        // 2. Java Stream API를 사용하여 '아파트 이름 + 도로명 주소'를 기준으로 그룹화합니다.
        Map<String, List<ApartmentTrade>> groupedByComplex = allTrades.stream()
                .filter(trade -> trade.getLatitude() != null && trade.getApartmentName() != null)
                .collect(Collectors.groupingBy(
                        trade -> trade.getApartmentName() + "_" + trade.getRoadNameAddress()
                ));

        // 3. 그룹화된 데이터를 바탕으로 DTO 리스트를 만듭니다.
        return groupedByComplex.values().stream()
                .map(tradesInComplex -> {
                    ApartmentTrade representative = tradesInComplex.get(0); // 대표 데이터 (좌표, 주소 등)
                    long count = tradesInComplex.size(); // 그룹 내 거래 건수
                    // 그룹 내 평균 거래 금액 계산
                    double avgAmount = tradesInComplex.stream()
                            .mapToDouble(t -> t.getDealAmount())                            .average()
                            .orElse(0.0);

                    return new ApartmentComplexDto(representative, count, avgAmount);
                })
                .collect(Collectors.toList());
    }

    public List<ApartmentTrade> findTradesByApartmentName(String aptName) {
        return apartmentTradeRepository.findByApartmentName(aptName);
    }
}