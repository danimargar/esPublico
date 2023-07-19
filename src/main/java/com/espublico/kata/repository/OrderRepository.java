package com.espublico.kata.repository;

import com.espublico.kata.model.OrderEntity;
import com.espublico.kata.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, OrderId> {

    @Query(value = "select region, count(*) as total from tde_order group by region order by total desc", nativeQuery = true)
    List<Object[]> totalRegion();
    @Query(value = "select country, count(*) as total from tde_order group by country order by total desc", nativeQuery = true)
    List<Object[]> totalCountry();
    @Query(value = "select item_type, count(*) as total from tde_order group by item_type order by total desc", nativeQuery = true)
    List<Object[]> totalItemType();
    @Query(value = "select sales_channel, count(*) as total from tde_order group by sales_channel order by total desc", nativeQuery = true)
    List<Object[]> totalSalesChannel();
    @Query(value = "select priority, count(*) as total from tde_order group by priority order by total desc", nativeQuery = true)
    List<Object[]> totalPriority();
}
