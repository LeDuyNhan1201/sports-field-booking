package org.jakartaee5g23.sportsfieldbooking.dtos.responses.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevenueReportResponse {

    @JsonIgnore
    String owner;

    double total;

    PaginateResponse<BookingResponse> data;

}
