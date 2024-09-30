package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SportFieldResponse {

    String id;

    String name;

    String location;

    Double pricePerHour;

    Integer opacity;

    Date openingTime;

    Date closingTime;

    Double rating;

    SportFieldStatus status;

    @JsonProperty(value = "category")
    String categoryName;

    UserResponse owner;

}
