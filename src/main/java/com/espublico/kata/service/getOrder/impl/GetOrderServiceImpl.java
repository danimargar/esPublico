package com.espublico.kata.service.getOrder.impl;

import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.getOrder.AbstractClient;
import com.espublico.kata.service.getOrder.GetOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GetOrderServiceImpl extends AbstractClient implements GetOrderService {

    public GetOrderServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public PageOrder getOrder(String link, String page, String maxPerPage) {

        String uri = "";
        if (link == null) {
            uri = baseUrl + "/orders" + "?page=" + page + "&max-per-page=" + maxPerPage ;
        } else {
            uri = link;
        }
        ResponseEntity<PageOrder> response = restTemplate.getForEntity(uri, PageOrder.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully user creation: {}", response.getBody().getContent());
            return response.getBody();
        }
        log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
        throw new RuntimeException("Error");
        }

    }

