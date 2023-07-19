package com.espublico.kata.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PageOrder {

    private Integer page;

    private List<Order> content;

    private Link links;

}
