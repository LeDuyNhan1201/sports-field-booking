package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.rating.RatingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.FieldAvailabilityAccessResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailabilityAccess;
import org.jakartaee5g23.sportsfieldbooking.entities.Rating;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingResponse toRatingResponse(Rating entity);

    @AfterMapping
    default void customizeDto(Rating entity, @MappingTarget RatingResponse dto) {
        dto.setMSportsField(SportsFieldMapper.INSTANCE.toSportsFieldResponse(entity.getSportsField()));
        dto.setMUser(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
        dto.setMBookingItem(BookingItemMapper.INSTANCE.toBookingItemResponse(entity.getBookingItem()));
    }
}
