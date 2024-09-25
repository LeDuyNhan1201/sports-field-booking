package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;

public record RevenueReportResponse(
    Double total,
    List<Order> orders
) {
}
