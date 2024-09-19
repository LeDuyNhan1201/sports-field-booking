package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findBySportFieldId(String id );
    @Query(value = "SELECT * FROM orders o WHERE o.sport_field_id = :id AND o.order_date BETWEEN :beginDate AND :endDate", nativeQuery = true)
    List<Order> findBySportFieldIdAndOrderDateBetween(
        @Param("id") String sportFieldId, 
        @Param("beginDate") Date beginDate, 
        @Param("endDate") Date endDate);
}