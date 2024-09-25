package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.*;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.SportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.ListSportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportFieldService {
    List<SportField> getAllField();
    SportFieldResponse addSportField(NewSportFieldRequest request);
    SportFieldResponse updateField(SportFieldRequest request);
    SportFieldResponse updateStatusField(String id, SportFieldStatus status);
    void updatePrice(SportField sportField, int price);
    List<Order> findOrderByFieldId(String id, Date beginDate, Date endDate);
    SportFieldResponse updateStatusOrder(String id, OrderStatus status);
    ListSportFieldResponse findOrderByStatus(OrderStatus status);
    RevenueReportResponse revenueReport(String id, Date beginDate, Date endDate);
}
