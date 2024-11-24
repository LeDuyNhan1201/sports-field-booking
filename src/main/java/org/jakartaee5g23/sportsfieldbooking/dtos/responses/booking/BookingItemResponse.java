package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;

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

    Date createdAt;

    BookingItemStatus status;

    double price;

    @JsonProperty(value = "createdBy")
    String createdBy;

    @JsonProperty(value = "sportField")
    SportsFieldResponse mSportsField;
}