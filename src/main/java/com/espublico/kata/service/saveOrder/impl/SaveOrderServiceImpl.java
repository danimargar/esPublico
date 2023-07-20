package com.espublico.kata.service.saveOrder.impl;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.model.OrderId;
import com.espublico.kata.repository.OrderRepository;
import com.espublico.kata.service.dto.Order;
import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.saveOrder.SaveOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaveOrderServiceImpl implements SaveOrderService {

    private static final Logger logger = LoggerFactory.getLogger(SaveOrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public SaveOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Number saveOrder(PageOrder pageOrder) throws Exception {
        logger.info("SaveOrderServiceImpl.saveOrder");
        List<OrderEntity> listOrders = new ArrayList<>();
        for (Order order: pageOrder.getContent()) {
            OrderEntity orderEntity = new OrderEntity();
            OrderId orderId = new OrderId();
            orderId.setUuid(order.getUuid());
            orderId.setId(BigDecimal.valueOf(order.getId()));
            orderEntity.setId(orderId);
            orderEntity.setRegion(order.getRegion());
            orderEntity.setCountry(order.getCountry());
            orderEntity.setItem_type(order.getItem_type());
            orderEntity.setSales_channel(order.getSales_channel());
            orderEntity.setPriority(order.getPriority());
            orderEntity.setOrder_date(order.getDate() != null ? dateFromString(order.getDate()) : null);
            orderEntity.setShip_date(order.getShip_date() != null ? dateFromString(order.getShip_date()) : null);
            orderEntity.setUnits_sold(BigDecimal.valueOf(order.getUnits_sold()));
            orderEntity.setUnit_price(BigDecimal.valueOf(order.getUnit_price()));
            orderEntity.setUnit_cost(BigDecimal.valueOf(order.getUnit_cost()));
            orderEntity.setTotal_revenue(BigDecimal.valueOf(order.getTotal_revenue()));
            orderEntity.setTotal_cost(BigDecimal.valueOf(order.getTotal_cost()));
            orderEntity.setTotal_profit(BigDecimal.valueOf(order.getTotal_profit()));

            listOrders.add(orderEntity);
        }

        String exception = "";
        List<OrderEntity>listResponse = null;
        int numIntentos = 0;
        while (listResponse == null && numIntentos < 3) {
            numIntentos++;
            try{
                logger.info("SaveOrderServiceImpl.saveOrder::Llamada");
                listResponse = orderRepository.saveAllAndFlush(listOrders);

            } catch(Exception e) {
                logger.error(e.getMessage());
                exception = e.getMessage();
            }
        }
        logger.info("SaveOrderServiceImpl.saveOrder::respuesta");
        if (listResponse == null) {
            throw new Exception(exception);

        }
        return listResponse.size();
    }




    @Override
    public Boolean isEmpty() {
        return orderRepository.count() == 0;
    }



    private Date dateFromString(String date) {
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
