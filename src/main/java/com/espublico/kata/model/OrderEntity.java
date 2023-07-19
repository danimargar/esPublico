package com.espublico.kata.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="tde_order")
public class OrderEntity {

    @EmbeddedId
    private OrderId id;

    @Column(name = "region")
    private String region;
    @Column(name = "country")
    private String country;
    @Column(name = "item_type")
    private String item_type;
    @Column(name = "sales_channel")
    private String sales_channel;
    @Column(name = "priority")
    private String priority;
    @Column(name = "order_date")
    private Date order_date;
    @Column(name = "ship_date")
    private Date ship_date;
    @Column(name = "units_sold")
    private Number units_sold;
    @Column(name = "unit_price")
    private Number unit_price;
    @Column(name = "unit_cost")
    private Number unit_cost;
    @Column(name = "total_revenue")
    private Number total_revenue;
    @Column(name = "total_cost")
    private Number total_cost;
    @Column(name = "total_profit")
    private Number total_profit;

}
