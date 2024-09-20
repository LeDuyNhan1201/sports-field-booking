package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.*;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportFieldService {
    List<SportField> getAllField();
    void updateField(SportField sportField);
    void deleteField(SportField sportField);
    void updatePrice(SportField sportField, int price);
    void updateStatusField(SportField sportField, SportFieldStatus status);
    List<Order> findOrderByFieldId(String id, Date beginDate, Date endDate);
    void updateStatusOrder(Order order, OrderStatus status);
    Double revenueReport(String id, Date beginDate, Date endDate);
}
