package org.jakartaee5g23.sportsfieldbooking.controllers;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.OrderRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.OrderResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.SignUpResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Order APIs")
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<OrderResponse> getOrderList() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new OrderResponse(getLocalizedMessage("order_list_success")));
    }

    @PostMapping("/my-orders")
    public ResponseEntity<OrderResponse> getMyOrderList(@RequestBody OrderRequest orderRequest) {
        String id = orderRequest.getId();
        List<Order> orders = orderRepository.findAllById(id);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OrderResponse(getLocalizedMessage("order_list_empty")));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new OrderResponse(getLocalizedMessage("order_list_success")));
    }

}