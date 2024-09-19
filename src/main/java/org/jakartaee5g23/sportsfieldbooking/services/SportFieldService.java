package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.*;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportFieldService {
    List<SportField> getAllField();
    void updateField(SportField sportField);
    void deleteField(SportField sportField);
    void setPrice(SportField sportField, int price);
    void setStatusField(SportField sportField, SportFieldStatus status);
    List<Order> getOrderField(String id, Date beginDate, Date endDate);
    Double getRevenue(String id, Date beginDate, Date endDate);
}
