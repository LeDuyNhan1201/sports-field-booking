package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {

    String id;

    String email;

    String username;

    String firstName;

    String lastName;

    String middleName;

}