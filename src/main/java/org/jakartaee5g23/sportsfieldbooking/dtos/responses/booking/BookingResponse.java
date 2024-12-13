package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    String id;

    Date orderDate;

    Date createdAt;

    @JsonIgnore
    BookingStatus status;

    @JsonProperty(value = "user")
    UserResponse mUser;

    @JsonProperty(value = "sportField")
    SportsFieldResponse mSportField;

    @JsonProperty(value = "fieldAvailability")
    FieldAvailabilityResponse mFieldAvailability;

    @JsonProperty(value = "bookingItems")
    List<BookingItemResponse> mBookingItems;

}