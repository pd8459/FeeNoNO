package com.example.feenono.realestate;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class MolitApiResponse {

    @XmlElement(name = "header")
    private ApiHeader header;

    @XmlElement(name = "body")
    private ApiBody body;
}