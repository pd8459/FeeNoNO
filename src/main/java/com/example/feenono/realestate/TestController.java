package com.example.feenono.realestate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ApiDataService apiDataService;

    /**
     * http://localhost:8080/load-data 주소로 접속하면
     * ApiDataService를 실행하여 데이터를 불러옵니다.
     */
    @GetMapping("/load-data")
    public ResponseEntity<String> loadData() {

        // "11110" = 서울시 종로구
        // "202407" = 2024년 7월
        // (필요시 이 값을 바꿔서 테스트하세요)
        String lawdCd = "11110";
        String dealYmd = "202407";

        System.out.println(">>>>> 데이터 로딩 요청 받음: " + lawdCd + ", " + dealYmd);

        // 서비스 실행!
        try {
            apiDataService.fetchAndSaveData(lawdCd, dealYmd);
            return ResponseEntity.ok(dealYmd + " 데이터 로딩 및 지오코딩 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("데이터 로딩 실패: " + e.getMessage());
        }
    }
}