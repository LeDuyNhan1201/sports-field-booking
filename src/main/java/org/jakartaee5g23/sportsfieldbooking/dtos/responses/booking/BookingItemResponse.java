package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingItemResponse {

    String id;

    Date availableDate;

    Date startTime;

    Date endTime;

    double price;

    @JsonProperty(value = "createdBy")
    String createdBy;

    @JsonProperty(value = "fieldAvailability")
    FieldAvailabilityResponse mFieldAvailability;
}