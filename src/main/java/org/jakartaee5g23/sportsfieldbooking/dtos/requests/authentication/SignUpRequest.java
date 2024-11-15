package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jakartaee5g23.sportsfieldbooking.enums.Gender;

import java.time.LocalDate;

public record SignUpRequest (

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String firstName,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String lastName,

        String middleName,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String username,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        @Email(message = "invalid_email")
        String email,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        @Size(min = 6, max = 20, message = "size_field")
        String password,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        @Size(min = 6, max = 20, message = "size_field")
        String passwordConfirmation,

        @NotNull(message = "null_field")
        @NotBlank(message = "blank_field")
        String mobileNumber,

        LocalDate birthdate,

        Gender gender,
                
        boolean acceptTerms

) {

}