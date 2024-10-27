package org.jakartaee5g23.sportsfieldbooking.dtos.responses.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    String id;

    String email;

    String username;

    String firstName;

    String lastName;

    String middleName;

    String mobileNumber;

    @JsonProperty("avatar")
    String mAvatar;

    @JsonProperty("roles")
    List<String> mRoles;

}