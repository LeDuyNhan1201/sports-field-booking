package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {

    String id;

    Date orderDate;

    BookingStatus status;

    @JsonProperty(value = "user")
    UserResponse mUser;

    @JsonProperty(value = "sportField")
    SportFieldResponse mSportField;

//    @JsonProperty(value = "fieldAvailability")
//    FieldAvailabilityResponse mFieldAvailability;

}
