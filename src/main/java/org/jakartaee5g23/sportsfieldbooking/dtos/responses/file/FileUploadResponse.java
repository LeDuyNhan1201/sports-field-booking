package org.jakartaee5g23.sportsfieldbooking.dtos.responses.file;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadResponse {

    String objectKey;

    String contentType;

    long size; // in bytes

    int chunkNumber;

    int totalChunks;

    Date createdAt;

}