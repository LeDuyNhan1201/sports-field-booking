package org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {

    String id;

    String comment;

    @JsonProperty("user")
    UserResponse mUser;

    @JsonProperty("sportField")
    SportsFieldResponse mSportField;

}