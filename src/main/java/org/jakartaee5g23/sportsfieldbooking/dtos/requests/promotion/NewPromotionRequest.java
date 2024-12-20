package org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;

public record NewPromotionRequest(

        @NotNull(message = "null_field") @NotBlank(message = "blank_field") String name,

        @NotNull(message = "null_field") @NotBlank(message = "blank_field") String description,

        @NotNull(message = "null_field") @Min(value = 0, message = "min_percent") Double discountPercentage,

        @NotNull(message = "null_field") Date startDate,

        @NotNull(message = "null_field") Date endDate,

        @NotNull(message = "null_field") PromotionStatus status,

        Boolean isConfirmed) {
}