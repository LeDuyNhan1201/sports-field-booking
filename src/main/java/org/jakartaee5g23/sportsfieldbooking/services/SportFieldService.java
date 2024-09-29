package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.springframework.data.domain.Page;

public interface SportFieldService {

    SportField findById(String id);

    Page<SportField> findAll(int offset, int limit);

    SportField create(SportField request, boolean isConfirmed);

    SportField update(SportField request, boolean isConfirmed);

    SportField updateStatus(String id, SportFieldStatus status);

    SportField updatePrice(String id, int price);

    RevenueReportResponse revenueReport(SportField sportField, Date beginDate, Date endDate, int offset, int limit);

}
