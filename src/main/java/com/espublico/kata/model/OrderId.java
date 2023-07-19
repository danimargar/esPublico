package com.espublico.kata.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Validated
@Getter
@Setter
@Embeddable
public class OrderId implements Serializable {

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "orderid", nullable = false)
    private BigDecimal id;


}
