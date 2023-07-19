package com.espublico.kata.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Order {

    private String uuid;
    private Integer id;
    private String region;
    private String country;
    private String item_type;
    private String sales_channel;
    private String priority;
    private String date;
    private String ship_date;
    private Integer units_sold;
    private Number unit_price;
    private Number unit_cost;
    private Number total_revenue;
    private Number total_cost;
    private Number total_profit;
    private Link links;



}
