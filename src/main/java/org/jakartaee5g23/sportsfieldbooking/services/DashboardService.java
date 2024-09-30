package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.dashboard.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface DashboardService {

    RevenueReportResponse revenueReport(SportField sportField, Date beginDate, Date endDate, int offset, int limit);

}
