package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.UpdateSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SportFieldMapper {
    SportFieldMapper INSTANCE = Mappers.getMapper(SportFieldMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "fieldAvailabilities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    SportField toSportField(NewSportFieldRequest dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "fieldAvailabilities", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    SportField toSportField(UpdateSportFieldRequest dto);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "owner", expression = "java(UserMapper.INSTANCE.toUserResponse(sportField.getUser()))")
    SportFieldResponse toSportFieldResponse(SportField sportField);

    @AfterMapping
    default void customizeDto(SportField entity, @MappingTarget SportFieldResponse dto) {
        dto.setCategoryName(entity.getCategory().getName());
        dto.setOwner(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
    }
}