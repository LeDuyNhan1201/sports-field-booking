package org.jakartaee5g23.sportsfieldbooking.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

}