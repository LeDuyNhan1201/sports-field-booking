package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SportsFieldResponse {

    String id;

    String name;

    String location;

    Integer opacity;

    Date openingTime;

    Date closingTime;

    Double rating;

    SportsFieldStatus status;

    @JsonProperty(value = "category")
    String categoryName;

    UserResponse owner;

    @JsonProperty(value = "images")
    List<String> mImages;

    @JsonProperty(value = "fieldAvailabilities")
    List<FieldAvailabilityResponse> fieldAvailabilities;

    @JsonProperty(value = "promotion")
    Promotion promotion;
}
