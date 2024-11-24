package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldAvailabilityAccessResponse {
    String id;

    Date startDate;

    Date endDate;

    @JsonProperty(value = "sportsField")
    SportsFieldResponse mSportsField;
}
