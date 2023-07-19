package com.espublico.kata.service.getOrder;

import com.espublico.kata.service.dto.PageOrder;

public interface GetOrderService {

    PageOrder getOrder(String link, String page, String maxPerPage) throws Exception;
}
