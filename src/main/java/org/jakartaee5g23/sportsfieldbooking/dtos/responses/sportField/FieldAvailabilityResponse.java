package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;

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

    BookingStatus status;

    User user;

    SportsField mSportsField;

    @JsonProperty(value = "payment")
    Payment mPayment;

    @JsonProperty(value = "fieldAvailability")
    FieldAvailabilityResponse mFieldAvailability;

    @JsonProperty(value = "startTime")
    @Column(name = "start_time")
    Date startTime;

    @JsonProperty(value = "endTime")
    @Column(name = "end_time")
    Date endTime;
}