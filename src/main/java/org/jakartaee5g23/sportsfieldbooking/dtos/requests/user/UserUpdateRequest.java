package org.jakartaee5g23.sportsfieldbooking.dtos.requests.user;

import lombok.Data;
import org.jakartaee5g23.sportsfieldbooking.enums.Gender;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private LocalDate birthdate;
    private Gender gender;
    private UserStatus status;
    // private AppFileMetadata avatar;
}
