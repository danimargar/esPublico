package com.espublico.kata.service.getOrder.impl;

import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.getOrder.AbstractClient;
import com.espublico.kata.service.getOrder.GetOrderService;
import com.espublico.kata.service.saveOrder.impl.SaveOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GetOrderServiceImpl extends AbstractClient implements GetOrderService {

    private static final Logger logger = LoggerFactory.getLogger(GetOrderServiceImpl.class);

    public GetOrderServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public PageOrder getOrder(String link, String page, String maxPerPage) throws Exception {
        logger.info("GetOrderServiceImpl");
        String uri = "";
        if (link == null) {
            uri = baseUrl + "/orders" + "?page=" + page + "&max-per-page=" + maxPerPage ;
        } else {
            uri = link;
        }

        String exception = "";
        ResponseEntity<PageOrder> response = null;
        int numIntentos = 0;
        while (response == null && numIntentos < 6) {
            numIntentos++;
            try{
                logger.info("GetOrderServiceImpl::Llamada");
                response = restTemplate.getForEntity(uri, PageOrder.class);

            } catch(Exception e) {
                logger.error(e.getMessage());
                exception = e.getMessage();
            }
        }
        logger.info("GetOrderServiceImpl::Respuesta");

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new Exception(exception);

    }

    }

