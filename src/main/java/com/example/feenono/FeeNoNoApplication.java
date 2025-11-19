package com.example.feenono;

import com.example.feenono.realestate.GeocodingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FeeNoNoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeeNoNoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(GeocodingService geocodingService) {
        return args -> {
           // geocodingService.setCoordinatesForSampleData(); // <-- 여기!
        };
    }
}