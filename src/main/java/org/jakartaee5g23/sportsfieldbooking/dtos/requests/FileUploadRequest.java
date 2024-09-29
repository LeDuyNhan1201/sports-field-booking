package org.jakartaee5g23.sportsfieldbooking.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileUploadRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String ownerId

)
{ }
