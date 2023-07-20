package com.espublico.kata.controller;

import com.espublico.kata.service.createCSV.CreateCSVService;
import com.espublico.kata.service.dto.ResponseOrder;
import com.espublico.kata.service.getInfoFromBD.GetInfoFromBDService;
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
import java.io.IOException;

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
    private final GetInfoFromBDService getInfoFromBDService;
    private final CreateCSVService createCSVService;

    public OrderController(GetOrderService getOrderService, SaveOrderService saveOrderService, GetInfoFromBDService getInfoFromBDService, CreateCSVService createCSVService) {
        this.getOrderService = getOrderService;
        this.saveOrderService = saveOrderService;
        this.getInfoFromBDService = getInfoFromBDService;
        this.createCSVService = createCSVService;
    }

    @GetMapping
    public ResponseEntity<ResponseOrder> managementOrder () {
        int iteracion = 1;
        String link = null;
        boolean nextLink = true;
        ResponseOrder response = null;

        logger.info("OrderController******************Comienzo controlador************************");
        long iniciocontrolador = System.currentTimeMillis();
        if (saveOrderService.isEmpty()) {
            while(nextLink) {

                logger.info("OrderController******************Comienza Iteración: " + iteracion);
                /** 1.1 Se obtiene la lista de pedidos*/
                PageOrder pageOrder = null;
                try {
                    pageOrder = getOrderService.getOrder(link, page, maxPerPage);
                } catch (Exception e) {
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }

                /** 1.2 Se carga en BD la lista obtenida*/
                Number OrderSaves = null;
                try {
                    OrderSaves = saveOrderService.saveOrder(pageOrder);
                } catch (Exception e) {
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }

                /** 1.3 Se comprueba si existen más páginas de pedidos*/
                if (pageOrder.getLinks().getNext() == null) {
                    //Se ha finalizado el proceso de descarga-carga de pedidos desde servidor a BD
                    nextLink = false;
                } else{
                    //Se carga el enlace a la siguiente página
                    link = pageOrder.getLinks().getNext();
                }
                iteracion++;
            }
        } else {
            logger.info("OrderController::La base de datos está cargada, no es necesario volverlo a hacer");
        }
        long finPrimerpaso = System.currentTimeMillis();
        logger.info("OrderController::finaliza proceso de carga en BD");

        /** 2. Se recuperan los resultados obtenidos desde BD y se incorporan al objeto de respuesta*/
        long inicioSegundopaso = System.currentTimeMillis();
        response = getInfoFromBDService.getInfo();
        long finSegundopaso = System.currentTimeMillis();


        /** 3. Se vuelca la información en fichero*/
        long inicioTercerpaso = System.currentTimeMillis();
        try {
            createCSVService.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long finTercerpaso = System.currentTimeMillis();


        double tiempoPrimerPaso = (double) ((finPrimerpaso - iniciocontrolador)/1000);
        double tiempoSegundoPaso = (double) ((finSegundopaso - inicioSegundopaso)/1000);
        double tiempoTercerPaso = (double) ((finTercerpaso - inicioTercerpaso)/1000);
        double tiempoTotal = (double) ((finTercerpaso - iniciocontrolador)/1000);
        logger.info("*******************************************************************");
        logger.info("*********************** Control de tiempos ************************");
        logger.info("Duración primer paso : " + tiempoPrimerPaso + " segundos");
        logger.info("Duración segundo paso : " + tiempoSegundoPaso + " segundos");
        logger.info("Duración tercer paso : " + tiempoTercerPaso + " segundos");
        logger.info("Duración total proceso : " + tiempoTotal + " segundos");
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
