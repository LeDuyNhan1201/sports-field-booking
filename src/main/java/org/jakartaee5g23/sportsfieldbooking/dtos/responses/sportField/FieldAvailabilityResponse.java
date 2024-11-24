package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.FieldAvailabilityStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldAvailabilityResponse {

    String id;

    Date orderDate;

    FieldAvailabilityStatus status;

    User user;

    // SportsField mSportsField;

    @JsonProperty(value = "payment")
    Payment mPayment;

    @JsonProperty(value = "fieldAvailability")
    FieldAvailabilityResponse mFieldAvailability;

    // price_per_hour
    @JsonProperty(value = "price")
    @Column(name = "price")
    Double price;

    @JsonProperty(value = "startTime")
    @Column(name = "start_time")
    Date startTime;

    @JsonProperty(value = "endTime")
    @Column(name = "end_time")
    Date endTime;

    @JsonProperty(value = "is_available")
    @Column(name = "is_available")
    Boolean isAvailable;

    @JsonProperty(value = "sportsField")
    SportsFieldResponse mSportsField;
}