package com.espublico.kata.service;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.repository.OrderRepository;
import com.espublico.kata.service.dto.Link;
import com.espublico.kata.service.dto.Order;
import com.espublico.kata.service.dto.PageOrder;
import com.espublico.kata.service.saveOrder.impl.SaveOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeveOrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    SaveOrderServiceImpl service;

    @Test
    void saveOk() throws Exception {
        when(orderRepository.saveAllAndFlush(any())).thenReturn(getListResponse());
        Number number = service.saveOrder(createPageOrder());
        assertAll(
                () -> assertThat(number).isNotNull(),
                () -> assertThat(number).isEqualTo(1)
        );
    }

    private List<OrderEntity> getListResponse() {
        List<OrderEntity> listOrder = new ArrayList<>();
        OrderEntity order = new OrderEntity();

        listOrder.add(order);

        return listOrder;
    }

    private PageOrder createPageOrder() {
        PageOrder pageOrder = new PageOrder();

        pageOrder.setPage(1);
        List<Order> listContent = new ArrayList<>();
        Order order = new Order();
        order.setUuid("1858f59d-8884-41d7-b4fc-88cfbbf00c53");
        order.setId(1);
        order.setRegion("Europe");
        order.setCountry("Spain");
        order.setItem_type("Fruits");
        order.setSales_channel("offLine");
        order.setPriority("M");
        order.setDate("7/27/2012");
        order.setShip_date("7/28/2012");
        order.setUnits_sold(1593);
        order.setUnit_cost(new Double(6.92));
        order.setUnit_price(new Double(9.33));
        order.setTotal_revenue(new Double(1482.34));
        order.setTotal_cost(new Double(11023.56));
        order.setTotal_profit(new Double(3839.13));
        Link link = new Link();
        link.setSelf("https://kata-espublicotech.g3stiona.com:443/v1/orders/1858f59d-8884-41d7-b4fc-88cfbbf00c53");
        order.setLinks(link);
        listContent.add(order);
        pageOrder.setContent(listContent);
        Link linkTotal = new Link();
        linkTotal.setSelf("https://kata-espublicotech.g3stiona.com:443/v1/orders?page=1&max-per-page=100");
        linkTotal.setNext("https://kata-espublicotech.g3stiona.com:443/v1/orders?page=2&max-per-page=100");
        pageOrder.setLinks(linkTotal);
        return pageOrder;
    }
}
