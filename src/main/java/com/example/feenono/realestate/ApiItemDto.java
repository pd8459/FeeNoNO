package com.example.feenono.realestate;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiItemDto {

    @XmlElement(name = "aptNm")
    private String apartmentName;

    @XmlElement(name = "buildYear")
    private String buildYear;

    @XmlElement(name = "dealAmount")
    private String dealAmount;

    @XmlElement(name = "dealDay")
    private String dealDay;

    @XmlElement(name = "dealMonth")
    private String dealMonth;

    @XmlElement(name = "dealYear")
    private String dealYear;

    @XmlElement(name = "excluUseAr")
    private String exclusiveArea;

    @XmlElement(name = "floor")
    private String floor;

    @XmlElement(name = "jibun")
    private String jibun;

    @XmlElement(name = "sggCd")
    private String sigunguCode;

    @XmlElement(name = "umdNm")
    private String dongName;
}