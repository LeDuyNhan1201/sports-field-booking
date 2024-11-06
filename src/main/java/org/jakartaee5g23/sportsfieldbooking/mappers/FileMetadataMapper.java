package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.events.HandleFileEvent;
import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {

    FileMetadataMapper INSTANCE = Mappers.getMapper(FileMetadataMapper.class);

    FileMetadata toFileMetadata(HandleFileEvent dto);

}