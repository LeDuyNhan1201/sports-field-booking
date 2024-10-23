package org.jakartaee5g23.sportsfieldbooking.dtos.requests.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileUploadRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String fileName,

    int chunkNumber,

    int totalChunks,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String contentType

)
{ }
