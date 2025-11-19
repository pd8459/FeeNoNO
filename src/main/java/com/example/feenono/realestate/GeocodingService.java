package com.example.feenono.realestate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocodingService {

    private final ApartmentTradeRepository apartmentTradeRepository;
    private final RestTemplate restTemplate;

    // 카카오 개발자 사이트의 REST API 키를 입력하세요.
    private final String KAKAO_API_KEY = "90a64fa8992b38b85e9ce1b91d1eabdd";

    /**
     * DB에서 좌표가 없는 데이터 중 5,000건만 가져와서 좌표로 변환합니다.
     * 개발 및 테스트 단계에서 빠르게 데이터를 준비하기 위해 사용합니다.
     */
    @Transactional
    public void setCoordinatesForSampleData() {
        // Repository에 추가한 메소드를 사용하여 좌표가 없는 5000건의 데이터만 조회합니다.
        List<ApartmentTrade> tradesToUpdate = apartmentTradeRepository.findTop5000ByLatitudeIsNull();

        if (tradesToUpdate.isEmpty()) {
            System.out.println(">>>>> 좌표가 비어있는 데이터가 없습니다. 모든 데이터의 좌표 변환이 완료되었습니다.");
            return;
        }

        int successCount = 0;
        int totalCount = tradesToUpdate.size();

        System.out.println(">>>>> 총 " + totalCount + "건의 샘플 데이터에 대한 좌표 변환을 시작합니다.");

        for (int i = 0; i < totalCount; i++) {
            ApartmentTrade trade = tradesToUpdate.get(i);

            if (trade.getRoadNameAddress() == null || trade.getRoadNameAddress().isBlank()) {
                continue;
            }

            Map<String, Double> coords = getCoordinates(trade.getRoadNameAddress());
            if (coords != null) {
                trade.setLatitude(coords.get("latitude"));
                trade.setLongitude(coords.get("longitude"));
                successCount++;
            }

            if ((i + 1) % 100 == 0) {
                System.out.println(">>>>> 진행 상황: " + (i + 1) + " / " + totalCount);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("좌표 변환 작업 중 중단되었습니다.");
                break;
            }
        }

        System.out.println(">>>>> 좌표 변환 완료! 총 " + successCount + "건의 좌표가 성공적으로 업데이트되었습니다.");
    }

    /**
     * 주소 문자열을 받아 카카오 API를 통해 위도, 경도 좌표를 반환합니다.
     */
    public Map<String, Double> getCoordinates(String address) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map<String, Object>> documents = (List<Map<String, Object>>) response.getBody().get("documents");

            if (documents != null && !documents.isEmpty()) {
                Map<String, Object> doc = documents.get(0);
                Map<String, Double> coords = new HashMap<>();
                coords.put("latitude", Double.parseDouble(doc.get("y").toString()));
                coords.put("longitude", Double.parseDouble(doc.get("x").toString()));
                return coords;
            }
        } catch (Exception e) {
            System.err.println("주소 변환 실패: " + address + ", 에러: " + e.getMessage());
        }
        return null;
    }
}