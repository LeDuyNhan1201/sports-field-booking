package org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField;

import jakarta.validation.constraints.NotNull;
import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.User;

public record NewSportFieldRequest(
    String name,
    String location,
    @NotNull(message = "Opacity can't be null") Integer pricePerHour,
    @NotNull(message = "Opacity can't be null") Integer opacity,
    String closingTime,
    String openingTime,
    Integer categoryId,
    String userId,
    boolean isConfirmed) {
    }