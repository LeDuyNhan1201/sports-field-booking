package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.startTime > CURRENT_TIMESTAMP")
    List<Order> findUpcomingBookingsByUserId(String userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findAllById(String userId);

    List<Order> findByUserId(String userId);
}