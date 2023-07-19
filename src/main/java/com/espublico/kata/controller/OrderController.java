package com.espublico.kata.controller;

import com.espublico.kata.service.getOrder.GetOrderService;
import com.espublico.kata.service.dto.PageOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("listOrders")
public class OrderController {

    @Value("${kata.page}")
    protected String page;

    @Value("${kata.maxPerPage}")
    protected String maxPerPage;

    private final GetOrderService getOrderService;


    public OrderController(GetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @GetMapping
    public void getOrder () {

        String link = null;
        boolean nextLink = true;

        while(nextLink) {
            /** 1. Se obtiene la lista de pedidos*/
            PageOrder pageOrder = getOrderService.getOrder(link, page, maxPerPage);

            /** 2. Se carga en BD la lista obtenida*/

            /** 3. Se comprueba si existen más páginas de pedidos*/
            if (pageOrder.getLinks().getNext() == null) {
                //Se ha finalizado el proceso de descarga-carga de pedidos desde servidor a BD
                nextLink = false;
            } else{
                //Se carga el enlace a la siguiente página
                link = pageOrder.getLinks().getNext();
            }
        }

        /** 3. Se recuperan los resultados obtenidos*/

        /** 4. Se vuelca la información en fichero*/

    }

}
