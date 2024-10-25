package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.UpdateSportsFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.AppFileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SportsFieldMapper {

    SportsFieldMapper INSTANCE = Mappers.getMapper(SportsFieldMapper.class);

    SportsField toSportsField(NewSportsFieldRequest dto);

    SportsField toSportsField(UpdateSportsFieldRequest dto);

    SportsFieldResponse toSportsFieldResponse(SportsField entity);
    @AfterMapping
    default void customizeDto(SportsField entity, @MappingTarget SportsFieldResponse dto) {
        dto.setCategoryName(entity.getCategory().getName());
        dto.setOwner(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
        dto.setMImages(entity.getImages() != null ? entity.getImages().stream().map(AppFileMetadata::getUrl).collect(Collectors.toList()) : new ArrayList<>());
    }

}