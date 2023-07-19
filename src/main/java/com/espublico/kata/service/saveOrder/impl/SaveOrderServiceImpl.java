package com.espublico.kata.service.saveOrder.impl;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.model.OrderId;
import com.espublico.kata.repository.OrderRepository;
import com.espublico.kata.service.dto.Order;
import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.saveOrder.SaveOrderService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaveOrderServiceImpl implements SaveOrderService {

    private final OrderRepository orderRepository;

    public SaveOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Number saveOrder(PageOrder pageOrder) {

        List<OrderEntity> listOrders = new ArrayList<>();
        for (Order order: pageOrder.getContent()) {
            OrderEntity orderEntity = new OrderEntity();
            OrderId orderId = new OrderId();
            orderId.setUuid(order.getUuid());
            orderId.setId(Long.valueOf(order.getId()));
            orderEntity.setId(orderId);
            orderEntity.setRegion(order.getRegion());
            orderEntity.setCountry(order.getCountry());
            orderEntity.setItem_type(order.getItem_type());
            orderEntity.setSales_channel(order.getSales_channel());
            orderEntity.setPriority(order.getPriority());
            orderEntity.setOrder_date(order.getDate() != null ? dateFromString(order.getDate()) : null);
            orderEntity.setShip_date(order.getShip_date() != null ? dateFromString(order.getShip_date()) : null);
            orderEntity.setUnits_sold(order.getUnits_sold());
            orderEntity.setUnit_price(order.getUnit_price());
            orderEntity.setUnit_cost(order.getUnit_cost());
            orderEntity.setTotal_revenue(order.getTotal_revenue());
            orderEntity.setTotal_cost(order.getTotal_cost());
            orderEntity.setTotal_profit(order.getTotal_profit());

            listOrders.add(orderEntity);
        }
        List<OrderEntity>listResponse = null;
        try{
            listResponse = orderRepository.saveAllAndFlush(listOrders);

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return listResponse.size();
    }

    private Date dateFromString(String date) {
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
