package com.espublico.kata.repository;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, OrderId> {


}
