package com.espublico.kata.service.getInfoFromBD.impl;

import com.espublico.kata.repository.OrderRepository;
import com.espublico.kata.service.dto.ResponseOrder;
import com.espublico.kata.service.getInfoFromBD.GetInfoFromBDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetInfoFromBDServiceImpl implements GetInfoFromBDService {

    private static final Logger logger = LoggerFactory.getLogger(GetInfoFromBDServiceImpl.class);

    private final OrderRepository orderRepository;

    public GetInfoFromBDServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public ResponseOrder getInfo() {
        logger.info("GetInfoFromBDServiceImpl");
        ResponseOrder response = new ResponseOrder();

        Long total = orderRepository.count();
        response.setTotalOrder(Math.toIntExact(total));

        List<Object[]> listRegion = orderRepository.totalRegion();
        response.setRegion(setmapa(listRegion));

        List<Object[]> listCountry = orderRepository.totalCountry();
        response.setCountry(setmapa(listCountry));

        List<Object[]> listitemType = orderRepository.totalItemType();
        response.setItemType(setmapa(listitemType));

        List<Object[]> listSalesChannel = orderRepository.totalSalesChannel();
        response.setSalesChannel(setmapa(listSalesChannel));

        List<Object[]> listPriority = orderRepository.totalPriority();
        response.setOrderPriority(setmapa(listPriority));

        return response;
    }

    private Map<String, String> setmapa(List<Object[]> listRegion) {

        Map<String,String> mapaRegion = new HashMap<>();
        for (Object[] item : listRegion) {
            mapaRegion.put(item[0].toString(), item[1].toString());
        }
        return mapaRegion;
    }


}
