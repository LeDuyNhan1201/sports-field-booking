package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RevenueFieldRequest(
    String id,

    Date beginDate,

    Date endDate) {
}
