package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FieldAvailabilityMapper {
    FieldAvailabilityMapper INSTANCE = Mappers.getMapper(FieldAvailabilityMapper.class);

    FieldAvailabilityResponse toFieldAvailabilityResponse(FieldAvailability entity);


}