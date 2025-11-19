package com.example.feenono.realestate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @Value("${kakao.javascript.appkey}")
    private String kakaoAppKey;


    @GetMapping("/map")
    public String mapPage() {
        return "map";
    }
}