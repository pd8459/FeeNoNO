package com.example.feenono.realestate;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvParserService {

    private final ApartmentTradeRepository apartmentTradeRepository;
    private final String CSV_FOLDER_PATH = "csv_data/";

    @Transactional
    public void parseAndSave() {
        File folder = new File(CSV_FOLDER_PATH);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    System.out.println(">>>>> 파싱 시작: " + file.getName());
                    try (Reader reader = new FileReader(file, Charset.forName("EUC-KR"))) {

                        List<ApartmentTrade> trades = new CsvToBeanBuilder<ApartmentTrade>(reader)
                                .withType(ApartmentTrade.class)
                                .withSkipLines(16)
                                .withIgnoreLeadingWhiteSpace(true)
                                .build()
                                .parse();

                        for(ApartmentTrade trade : trades) {
                            String cleanedAmount = trade.getDealAmount().trim().replace(",", "");
                            trade.setDealAmount(cleanedAmount);
                        }

                        apartmentTradeRepository.saveAll(trades);
                        System.out.println(">>>>> " + file.getName() + " -> " + trades.size() + "건 DB 저장 완료!");

                    } catch (Exception e) {
                        System.err.println(file.getName() + " 파일 처리 중 오류 발생: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("\n>>>>> 모든 CSV 파일 처리가 완료되었습니다.");
        } else {
            System.err.println("csv_data 폴더를 찾을 수 없거나 비어있습니다.");
        }
    }
}