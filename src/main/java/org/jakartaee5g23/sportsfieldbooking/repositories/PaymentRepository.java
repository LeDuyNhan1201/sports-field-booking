package org.jakartaee5g23.sportsfieldbooking.repositories;

import java.util.Optional;

import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrderId(String orderId);

}