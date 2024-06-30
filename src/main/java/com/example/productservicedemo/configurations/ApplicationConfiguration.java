package com.example.productservicedemo.configurations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // This annotation tells Spring that this class is a configuration class
public class ApplicationConfiguration {

    @Bean
    public RestTemplate createRestTemplate(){

        return new RestTemplateBuilder().build();
    }
}
