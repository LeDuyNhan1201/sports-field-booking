package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.*;
import java.time.*;

import org.apache.tomcat.websocket.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.SportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.ListSportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.CategoryRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public Optional<Date> parseTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return Optional.of(formatter.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean isTimeGreater(Date openingTime, Date closingTime) {
        LocalTime openingLocalTime = openingTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime closingLocalTime = closingTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        return closingLocalTime.isAfter(openingLocalTime);
    }
    
    @Override
    public List<SportField> getAllField() {
        return sportFieldRepository.findAll();
    }

    @Override
    @Transactional
    public SportFieldResponse updateField(SportFieldRequest request) {
        if (request.isConfirmed()) {
            Date openingTime = parseTime(request.openingTime()).orElse(new Date());
            Date closingTime = parseTime(request.closingTime()).orElse(new Date());

            SportField sportField = sportFieldRepository.findById(request.id()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));

            if(request.opacity() < 1 )
                return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("opacity_must_be_greater_than_0"));

            if(request.pricePerHour() < 1 )
                return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("price_per_hour_must_be_greater_than_0"));
            
            if(!isTimeGreater(openingTime, closingTime)){
                return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("opening_hours_must_not_be_greater_than_closing_hours"));
            }
            sportField.setOpacity(request.opacity());
            sportField.setPricePerHour(request.pricePerHour());
            sportField.setClosingTime(closingTime);
            sportField.setOpeningTime(openingTime);
            sportField.setLocation(request.location());
            sportField.setName(request.name());

            sportFieldRepository.save(sportField);
            return new SportFieldResponse(HttpStatus.OK.value(),getLocalizedMessage("success"));
        }
        return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("update_failed"));
    }

    @Override
    public SportFieldResponse updateStatusField(String id, SportFieldStatus status) {
        SportField sportField = sportFieldRepository.findById(id).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
        sportField.setStatus(status);
        sportFieldRepository.save(sportField);            
        return new SportFieldResponse(HttpStatus.OK.value(),getLocalizedMessage("success"));
    }

    @Override
    public void updatePrice(SportField sportField, int price) {
        sportField.setPricePerHour(price);
    }

    @Override
    public List<Order> findOrderByFieldId(String id, Date beginDate, Date endDate) {
        return orderRepository.findBySportFieldIdAndStartTimeBetween(id, beginDate, endDate);
    }

    @Override
    public SportFieldResponse updateStatusOrder(String id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
            () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
        order.setStatus(status);
        orderRepository.save(order);
        return new SportFieldResponse(HttpStatus.OK.value(),getLocalizedMessage("success"));
    }

    @Override
    public ListSportFieldResponse  findOrderByStatus(OrderStatus status){
        List<Order> orders = orderRepository.findByStatus(status);
        return new ListSportFieldResponse(orders);
    }
    @Override
    public RevenueReportResponse revenueReport(String id, Date beginDate, Date endDate) {
        if(beginDate.compareTo(endDate) > 0)    
            return new RevenueReportResponse(0.0, null, getLocalizedMessage(""));
        Double total = 0.0;
        List<Order> orders = orderRepository.findBySportFieldIdAndStartTimeBetween(id, beginDate, endDate);
        for (Order order : orders) {
            Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Payment"));
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                total += payment.getPrice();
            }
        }
        return new RevenueReportResponse(total, orders, getLocalizedMessage("success"));
    }

    @Override
    public SportFieldResponse addSportField(NewSportFieldRequest request) {

        Date openingTime = parseTime(request.openingTime()).orElse(new Date());
        Date closingTime = parseTime(request.closingTime()).orElse(new Date());

        if(!isTimeGreater(openingTime, closingTime)){
            return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("opening_hours_must_not_be_greater_than_closing_hours"));
        }

        if(request.opacity() < 1 )
            return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("opacity_must_be_greater_than_0"));

        if(request.pricePerHour() < 1 )
            return new SportFieldResponse(HttpStatus.BAD_REQUEST.value(),getLocalizedMessage("price_per_hour_must_be_greater_than_0"));
            
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
            () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Category"));
        
        User user = userRepository.findById(request.userId()).orElseThrow(
            () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "User"));
    
        SportField createField = SportField.builder()
            .name(request.name())
            .location(request.location())
            .pricePerHour(request.pricePerHour())
            .opacity(request.opacity())
            .openingTime(openingTime)
            .closingTime(closingTime)
            .status(SportFieldStatus.NONE)
            .category(category)
            .user(user)
            .build();
        sportFieldRepository.save(createField);

        return new SportFieldResponse(HttpStatus.OK.value(),getLocalizedMessage("success"));
    }
}
