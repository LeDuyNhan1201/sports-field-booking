package org.jakartaee5g23.sportsfieldbooking.dtos.events;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jakartaee5g23.sportsfieldbooking.enums.FileMetadataType;
import org.jakartaee5g23.sportsfieldbooking.enums.HandleFileAction;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HandleFileEvent {

    String objectKey;

    long size;

    String contentType;

    String url;

    String ownerId;

    HandleFileAction action;

    FileMetadataType type;

}
