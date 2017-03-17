package com.pgs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by mmalek on 3/17/2017.
 */
@Configuration
public class ConverterConfig {

    @Autowired
    private ObjectMapper objectMapper; //reuse the pre-configured mapper

    @PostConstruct
    public void setup() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //whatever else you need
    }
}