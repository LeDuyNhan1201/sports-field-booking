package org.jakartaee5g23.sportsfieldbooking.dtos.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "null_field") @NotBlank(message = "blank_field") String name) {
}