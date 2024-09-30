package org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {

    TokensResponse tokensResponse;

    UserResponse user;

}