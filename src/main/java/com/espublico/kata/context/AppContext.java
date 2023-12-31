package com.espublico.kata.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppContext {

    @Bean
    RestTemplate restTemplate() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30)).
                setReadTimeout(Duration.ofSeconds(30)).build();
    }

}
