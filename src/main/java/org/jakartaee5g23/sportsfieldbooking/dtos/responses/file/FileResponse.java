package org.jakartaee5g23.sportsfieldbooking.dtos.responses.file;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileResponse {

    String uuid;

    String objectKey;

    String backupPath;

    String type;

    long size; // in bytes

    Date createdAt;

}