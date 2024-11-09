package org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentMethod;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String id;

    PaymentMethod method;

    Double price;

    PaymentStatus status;

    @JsonProperty(value = "booking")
    BookingResponse mBooking;
}
