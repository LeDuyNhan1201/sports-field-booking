package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityAccessResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface FieldAvailabilityAccessMapper {
    FieldAvailabilityAccessMapper INSTANCE = Mappers.getMapper(FieldAvailabilityAccessMapper.class);

    FieldAvailabilityAccessResponse toFieldAvailabilityAccessResponse(FieldAvailabilityAccess entity);

    @AfterMapping
    default void customizeDto(FieldAvailabilityAccess entity, @MappingTarget FieldAvailabilityAccessResponse dto) {
        dto.setMSportsField(
                SportsFieldMapper.INSTANCE
                        .toSportsFieldResponse(entity.getSportsField()));
    }
}
