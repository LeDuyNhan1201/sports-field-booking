package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAll();
}