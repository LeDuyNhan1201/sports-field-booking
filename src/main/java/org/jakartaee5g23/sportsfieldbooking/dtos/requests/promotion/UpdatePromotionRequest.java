package org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.text.*;
import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;

import java.time.*;


public record UpdatePromotionRequest(
    @NotNull(message = "null_field")
    Integer id,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String name,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String description,

    @NotNull(message = "null_field")
    Double discountPercentage,

    @NotNull(message = "null_field")
    Date startDate,

    @NotNull(message = "null_field")
    Date endDate,

    Boolean isConfirmed
) {
}