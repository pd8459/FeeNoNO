package com.example.feenono;

import com.example.feenono.realestate.CsvParserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FeeNoNoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeeNoNoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(CsvParserService csvParserService) {
        return args -> {
            csvParserService.parseAndSave();
        };
    }
}
