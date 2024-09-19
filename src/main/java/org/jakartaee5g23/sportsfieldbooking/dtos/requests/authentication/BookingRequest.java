package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record BookingRequest(

        @NotNull(message = "Start time can't be null")
        Date startTime,

        Double bookingHours,

        @NotNull(message = "End time can't be null")
        Date endTime,

        @NotNull(message = "User ID can't be null")
        String idUser,

        @NotNull(message = "Sport field ID cannot be blank")
        String idSportField,

        boolean isConfirmed

) {}
