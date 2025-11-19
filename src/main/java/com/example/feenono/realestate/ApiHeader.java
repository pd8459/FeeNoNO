package com.example.feenono.realestate;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiHeader {

    @XmlElement(name = "resultCode")
    private String resultCode;

    @XmlElement(name = "resultMsg")
    private String resultMsg;
}