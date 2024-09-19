package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

import org.apache.tomcat.websocket.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SportFieldServiceImpl implements SportFieldService {

    SportFieldRepository sportFieldRepository;
    OrderRepository orderRepository;
    PaymentRepository paymentRepository;

    @Override
    public List<SportField> getAllField() {
        return sportFieldRepository.findAll();
    }

    @Override
    public void updateField(SportField sportField) {
        sportFieldRepository.save(sportField);
    }

    @Override
    public void deleteField(SportField sportField) {
        sportFieldRepository.save(sportField);
    }

    @Override
    public void setPrice(SportField sportField, int price) {
        sportField.setPricePerHour(price);
    }

    @Override
    public void setStatusField(SportField sportField, SportFieldStatus status) {
        sportField.setStatus(status);
        sportFieldRepository.save(sportField);
    }

    @Override
    public List<Order> getOrderField(String id, Date beginDate, Date endDate) {
        return orderRepository.findBySportFieldIdAndOrderDateBetween(id, beginDate, endDate);
    }

    @Override
    public Double getRevenue(String id, Date beginDate, Date endDate) {
        Double total = 0.0;
        List<Order> orders = orderRepository.findBySportFieldIdAndOrderDateBetween(id, beginDate, endDate);
        for (Order order : orders) {
            // Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow(() -> 
            //     );
            // if(payment.getStatus() == PaymentStatus.COMPLETED){
            //     total += payment.getPrice();
            // }
        }
        return total;
    }
}
