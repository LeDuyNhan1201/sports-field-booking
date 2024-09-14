package org.jakartaee5g23.sportsfieldbooking.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {

    String id;

    String username;

    String firstName;

    String lastName;

    String middleName;

    @JsonProperty(value = "birthdate")
    String strBirthdate;

}