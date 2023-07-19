package com.espublico.kata.service.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseOrder {

    private Map<String, String> region;
    private Map<String, String> country;
    private Map<String, String> itemType;
    private Map<String, String> salesChannel;
    private Map<String, String> orderPriority;
    private Integer totalOrder;
}
