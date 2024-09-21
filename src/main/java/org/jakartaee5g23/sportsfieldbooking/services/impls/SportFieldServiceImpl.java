package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

import org.apache.tomcat.websocket.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

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
    @Transactional
    public SportFieldResponse updateField(SportFieldRequest request) {
        if (request.isConfirmed()) {
            SportField sportField = sportFieldRepository.findById(request.id()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
            sportField.setOpacity(request.opacity());
            sportField.setPricePerHour(request.pricePerHour());
            sportField.setClosingTime(request.closingTime());
            sportField.setOpeningTime(request.openingTime());
            sportField.setLocation(request.location());
            sportField.setName(request.name());

            sportFieldRepository.save(sportField);
            return new SportFieldResponse(getLocalizedMessage("success"));
        }
        return new SportFieldResponse(getLocalizedMessage("update failed"));
    }

    @Override
    public SportFieldResponse deleteField(String id) {
        SportField sportField = sportFieldRepository.findById(id).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
        sportField.setStatus(SportFieldStatus.BANNED);
        sportFieldRepository.save(sportField);            
        return new SportFieldResponse(getLocalizedMessage("success"));

    }

    @Override
    public void updatePrice(SportField sportField, int price) {
        sportField.setPricePerHour(price);
    }

    @Override
    public void updateStatusField(SportField sportField, SportFieldStatus status) {
        sportField.setStatus(status);
        sportFieldRepository.save(sportField);
    }

    @Override
    public List<Order> findOrderByFieldId(String id, Date beginDate, Date endDate) {
        return orderRepository.findBySportFieldIdAndStartTimeBetween(id, beginDate, endDate);
    }

    @Override
    public void updateStatusOrder(Order order, OrderStatus status) {
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public Double revenueReport(String id, Date beginDate, Date endDate) {
        Double total = 0.0;
        List<Order> orders = orderRepository.findBySportFieldIdAndStartTimeBetween(id, beginDate, endDate);
        for (Order order : orders) {
            Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Payment"));
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                total += payment.getPrice();
            }
        }
        return total;
    }
}
