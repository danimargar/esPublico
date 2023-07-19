package com.espublico.kata.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    private BigDecimal units_sold;
    @Column(name = "unit_price")
    private BigDecimal unit_price;
    @Column(name = "unit_cost")
    private BigDecimal unit_cost;
    @Column(name = "total_revenue")
    private BigDecimal total_revenue;
    @Column(name = "total_cost")
    private BigDecimal total_cost;
    @Column(name = "total_profit")
    private BigDecimal total_profit;

}
