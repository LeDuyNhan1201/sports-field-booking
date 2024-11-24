package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
