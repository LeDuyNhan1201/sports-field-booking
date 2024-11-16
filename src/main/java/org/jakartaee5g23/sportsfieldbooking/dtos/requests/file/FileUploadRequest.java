package org.jakartaee5g23.sportsfieldbooking.dtos.requests.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jakartaee5g23.sportsfieldbooking.enums.FileMetadataType;

public record FileUploadRequest (

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String fileMetadataId,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String ownerId,

    FileMetadataType fileMetadataType,

    String chunkHash,

    long startByte,

    long totalSize,

    @NotNull(message = "null_field")
    @NotBlank(message = "blank_field")
    String contentType

)
{ }
