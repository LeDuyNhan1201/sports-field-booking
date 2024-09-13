package org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.dtos.Tokens;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.UserInfoResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {

    Tokens tokens;

    UserInfoResponse user;

}