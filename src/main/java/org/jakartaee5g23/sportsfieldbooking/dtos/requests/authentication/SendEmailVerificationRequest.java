package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jakartaee5g23.sportsfieldbooking.enums.VerificationType;

public record SendEmailVerificationRequest (

        @NotNull(message = "null_email")
        @NotBlank(message = "blank_email")
        @Email(message = "invalid_email")
        String email,

        @NotNull(message = "null_verification_type")
        VerificationType type

) {
}
