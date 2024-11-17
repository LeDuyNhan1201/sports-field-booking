package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.checkerframework.checker.units.qual.A;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.fieldAvailability.NewFieldAvailabilityRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FieldAvailabilityMapper {
    FieldAvailabilityMapper INSTANCE = Mappers.getMapper(FieldAvailabilityMapper.class);

    FieldAvailability toFieldAvailability(NewFieldAvailabilityRequest dto);

    FieldAvailabilityResponse toFieldAvailabilityResponse(FieldAvailability entity);

    @AfterMapping
    default void customizeDto(FieldAvailability entity, @MappingTarget FieldAvailabilityResponse dto) {
        dto.setMSportsField(
                SportsFieldMapper.INSTANCE
                        .toSportsFieldResponse(entity.getSportsField()));
    }

}