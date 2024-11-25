package org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication;

import java.util.Date;

public record SendEmailForgotPasswordResponse(

    String message,

    int retryAfter

) {

}