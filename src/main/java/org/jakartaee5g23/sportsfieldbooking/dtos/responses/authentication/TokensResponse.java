package org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication;

public record TokensResponse(

    String accessToken,
    String refreshToken

) {
}
