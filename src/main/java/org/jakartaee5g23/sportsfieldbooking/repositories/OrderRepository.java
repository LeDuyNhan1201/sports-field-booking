package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.*;
import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAll();
}