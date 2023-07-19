package com.espublico.kata.controller;

import com.espublico.kata.service.getOrder.GetOrderService;
import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.saveOrder.SaveOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("listOrders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${kata.page}")
    protected String page;

    @Value("${kata.maxPerPage}")
    protected String maxPerPage;

    private final GetOrderService getOrderService;

    private final SaveOrderService saveOrderService;


    public OrderController(GetOrderService getOrderService, SaveOrderService saveOrderService) {
        this.getOrderService = getOrderService;
        this.saveOrderService = saveOrderService;
    }

    @GetMapping
    public ResponseEntity<Object> managementOrder () {
        int iteracion = 1;
        String link = null;
        boolean nextLink = true;
        logger.info("OrderController******************Comienzo controlador************************");
        while(nextLink) {

            logger.info("OrderController******************Comienza Iteración: " + iteracion);
            /** 1. Se obtiene la lista de pedidos*/
            PageOrder pageOrder = null;
            try {
                pageOrder = getOrderService.getOrder(link, page, maxPerPage);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            /** 2. Se carga en BD la lista obtenida*/
            Number OrderSaves = null;
            try {
                OrderSaves = saveOrderService.saveOrder(pageOrder);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            /** 3. Se comprueba si existen más páginas de pedidos*/
            if (pageOrder.getLinks().getNext() == null) {
                //Se ha finalizado el proceso de descarga-carga de pedidos desde servidor a BD
                nextLink = false;
            } else{
                //Se carga el enlace a la siguiente página
                link = pageOrder.getLinks().getNext();
            }
            iteracion++;
        }
        logger.info("OrderController::finaliza proceso de carga en BD");
        /** 3. Se recuperan los resultados obtenidos*/

        /** 4. Se vuelca la información en fichero*/

        return null;
    }

}
