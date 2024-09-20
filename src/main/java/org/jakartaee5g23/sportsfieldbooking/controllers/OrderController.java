package org.jakartaee5g23.sportsfieldbooking.controllers;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.OrderResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.ResetPasswordResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> OrderList() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new OrderResponse(getLocalizedMessage("order_success")));
    }
}