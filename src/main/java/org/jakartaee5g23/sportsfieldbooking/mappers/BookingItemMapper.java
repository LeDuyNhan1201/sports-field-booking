package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingItemResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {

        BookingItemMapper INSTANCE = Mappers.getMapper(BookingItemMapper.class);

        BookingItemResponse toBookingItemResponse(BookingItem entity);

        @AfterMapping
        default void customizeDto(BookingItem entity, @MappingTarget BookingItemResponse dto) {
                dto.setMFieldAvailability(
                                FieldAvailabilityMapper.INSTANCE
                                                .toFieldAvailabilityResponse(entity.getFieldAvailability()));
                // dto.setMSportField(
                // SportsFieldMapper.INSTANCE
                // .toSportsFieldResponse(entity.getFieldAvailability().getSportsField()));
                // dto.setMFieldAvailability(
                // FieldAvailabilityMapper.INSTANCE
                // .toFieldAvailabilityResponse(entity.getFieldAvailability()));
        }
}
