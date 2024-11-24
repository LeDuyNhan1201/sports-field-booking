package org.jakartaee5g23.sportsfieldbooking.dtos.responses.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingItemResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingResponse {
    String id;

    Double rating_point;

    @JsonProperty(value = "User")
    UserResponse mUser;

    @JsonProperty(value = "Sports field")
    SportsFieldResponse mSportsField;

    @JsonProperty(value = "Booking item")
    BookingItemResponse mBookingItem;
}
