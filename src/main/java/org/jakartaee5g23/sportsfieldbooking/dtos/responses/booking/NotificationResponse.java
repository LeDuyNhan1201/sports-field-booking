package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    String id;

    @JsonProperty("booking")
    BookingResponse mBooking;

    @JsonProperty("review")
    ReviewResponse mReview;

    NotificationType type;

    String message;

    boolean isRead;

    @JsonProperty(value = "sportField")
    SportsFieldResponse mSportField;
}