package com.example.feenono.realestate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

// ★ JAXB 디코더/인코더 클래스 import는 그대로 유지합니다.
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    // 기존의 .defaultCodecs().jaxb2XmlDecoder() 호출 대신,
                    // customCodecs().register()를 사용해 코덱을 명시적으로 등록합니다.
                    // 이 방식이 API 버전에 덜 민감하고 충돌을 피할 수 있습니다.
                    configurer.customCodecs().register(new Jaxb2XmlDecoder());
                    configurer.customCodecs().register(new Jaxb2XmlEncoder());

                })
                .build();

        // 주입받은 Builder에 XML 코덱 전략을 적용합니다.
        return builder
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}