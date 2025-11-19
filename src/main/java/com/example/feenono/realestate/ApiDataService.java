package com.example.feenono.realestate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiDataService {

    private final WebClient webClient;
    private final ApartmentTradeRepository apartmentTradeRepository;
    private final GeocodingService geocodingService;

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final String API_URL = "https://apis.data.go.kr/1613000/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade";

    public void fetchAndSaveData(String lawdCd, String dealYmd) {

        MolitApiResponse response = webClient.get()
                .uri(API_URL, uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("LAWD_CD", lawdCd)
                        .queryParam("DEAL_YMD", dealYmd)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .build())
                .retrieve()
                .bodyToMono(MolitApiResponse.class)
                .block();

        if (response == null || response.getBody() == null || response.getBody().getItems() == null || response.getBody().getItems().getItemList() == null) {
            System.out.println("API 응답 데이터가 없거나 형식이 잘못되었습니다.");
            return;
        }

        List<ApiItemDto> items = response.getBody().getItems().getItemList();
        List<ApartmentTrade> tradesToSave = items.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());

        System.out.println(">> 지오코딩 시작 (총 " + tradesToSave.size() + "건)...");
        int successCount = 0;
        for (ApartmentTrade trade : tradesToSave) {

            Map<String, Double> coords = geocodingService.getCoordinates(trade.getRoadNameAddress());

            if (coords != null) {
                trade.setLongitude(coords.get("longitude"));
                trade.setLatitude(coords.get("latitude"));
                successCount++;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        System.out.println(">> 지오코딩 완료 (성공: " + successCount + "건)");

        apartmentTradeRepository.saveAll(tradesToSave);

        System.out.println(tradesToSave.size() + "건의 데이터 DB 저장 완료!");
    }

    private ApartmentTrade mapDtoToEntity(ApiItemDto dto) {
        ApartmentTrade trade = new ApartmentTrade();

        trade.setApartmentName(dto.getApartmentName().trim());

        String address = dto.getDongName() + " " + dto.getJibun();
        trade.setRoadNameAddress(address);

        try {
            String cleanedAmount = dto.getDealAmount().replace(",", "").trim();
            trade.setDealAmount(Integer.parseInt(cleanedAmount));
        } catch (NumberFormatException e) {
            trade.setDealAmount(0);
        }

        try {
            String month = String.format("%02d", Integer.parseInt(dto.getDealMonth()));
            trade.setDealYearMonth(Integer.parseInt(dto.getDealYear() + month));

            trade.setDealDay(Integer.parseInt(dto.getDealDay().trim()));
            trade.setFloor(Integer.parseInt(dto.getFloor().trim()));
            trade.setExclusiveArea(Double.parseDouble(dto.getExclusiveArea().trim()));
            trade.setBuildYear(Integer.parseInt(dto.getBuildYear().trim()));
        } catch (Exception e) {
            e.printStackTrace();
            if(trade.getDealYearMonth() == 0) trade.setDealYearMonth(200001);
        }

        trade.setLatitude(null);
        trade.setLongitude(null);

        return trade;
    }
}