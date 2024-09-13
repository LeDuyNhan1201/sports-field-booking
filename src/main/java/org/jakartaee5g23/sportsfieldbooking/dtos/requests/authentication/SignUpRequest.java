package org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jakartaee5g23.sportsfieldbooking.enums.Gender;

import java.time.LocalDate;

public record SignUpRequest(

        @NotNull(message = "null_name")
        @NotBlank(message = "blank_name")
        String firstName,

        @NotNull(message = "null_name")
        @NotBlank(message = "blank_name")
        String lastName,

        String middleName,

        @NotNull(message = "null_name")
        @NotBlank(message = "blank_name")
        String username,

        @NotNull(message = "null_email")
        @NotBlank(message = "blank_email")
        @Email(message = "invalid_email")
        String email,

        @NotNull(message = "null_password")
        @NotBlank(message = "blank_password")
        @Size(min = 6, max = 20, message = "size_password")
        String password,

        @NotNull(message = "null_password")
        @NotBlank(message = "blank_password")
        @Size(min = 6, max = 20, message = "size_password")
        String passwordConfirmation,

        @NotBlank(message = "blank_phone_number")
        @NotNull(message = "null_phone_number")
        String mobileNumber,

        LocalDate birthdate,

        Gender gender,

        boolean acceptTerms

) {

}