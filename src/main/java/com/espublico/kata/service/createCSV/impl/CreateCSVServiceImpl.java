package com.espublico.kata.service.createCSV.impl;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.repository.OrderRepository;
import com.espublico.kata.service.createCSV.CreateCSVService;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCSVServiceImpl implements CreateCSVService {

    private static final Logger logger = LoggerFactory.getLogger(CreateCSVServiceImpl.class);

    private final OrderRepository orderRepository;

    @Value("${kata.since}")
    protected int since;

    @Value("${kata.numRow}")
    protected int numRow;
    public CreateCSVServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void create() throws IOException {
        logger.info("CreateCSVServiceImpl");
        String exception = "";
        List<OrderEntity> listOrderEntity = new ArrayList<>();
        CSVWriter writer = new CSVWriter(new FileWriter("Order.csv"));
        String[] headers = {"Order ID", "Order Priority", "Order Date", "Region", "Country", "Item Type", "Sales Channel", "Ship Date", "Units Sold", "Unit Price", "Unit Cost", "Total Revenue", "Total Cost", "Total Profit"};
        writer.writeNext(headers);
        int contador = 0;
        while (listOrderEntity != null) {
            try{
                logger.info("iteración::" + contador);
                logger.info("CreateCSVServiceImpl::Llamada");
                listOrderEntity = orderRepository.findAllByOrderByName(since, numRow);
                logger.info("CreateCSVServiceImpl::Respuesta");
                //Volcado en fichero
                List<String[]> listAr = setListArray(listOrderEntity);
                writer.writeAll((listAr));

            } catch(Exception e) {
                logger.error(e.getMessage());
                exception = e.getMessage();
            }
            since = since + numRow + 1;
            contador++;
        }
        writer.close();
        logger.info("CreateCSVServiceImpl::Finaliza proceso de paginación");


    }

    private List<String[]> setListArray(List<OrderEntity> listOrderEntity) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<String[]> listAr = new ArrayList<>();
        for (OrderEntity order : listOrderEntity) {
            String[] ar = new String[14];

            ar[0] = String.valueOf(order.getId().getId());
            ar[1] = order.getPriority();
            ar[2] = dateFormat.format(order.getOrder_date());
            ar[3] = order.getRegion();
            ar[4] = order.getCountry();
            ar[5] = order.getItem_type();
            ar[6] = order.getSales_channel();
            ar[7] = dateFormat.format(order.getShip_date());
            ar[8] = String.valueOf(order.getUnits_sold());
            ar[9] = String.valueOf(order.getUnit_price().doubleValue());
            ar[10] = String.valueOf(order.getUnit_cost().doubleValue());
            ar[11] = String.valueOf(order.getTotal_revenue().doubleValue());
            ar[12] = String.valueOf(order.getTotal_cost().doubleValue());
            ar[13] = String.valueOf(order.getTotal_profit().doubleValue());

            listAr.add(ar);
        }

        return listAr;
    }

}
