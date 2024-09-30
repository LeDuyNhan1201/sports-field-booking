package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.NewSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.sportField.UpdateSportFieldRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SportFieldMapper {

    SportFieldMapper INSTANCE = Mappers.getMapper(SportFieldMapper.class);

    SportField toSportField(NewSportFieldRequest dto);

    SportField toSportField(UpdateSportFieldRequest dto);

    SportFieldResponse toSportFieldResponse(SportField entity);
    @AfterMapping
    default void customizeDto(SportField entity, @MappingTarget SportFieldResponse dto) {
        dto.setCategoryName(entity.getCategory().getName());
        dto.setOwner(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
    }

}