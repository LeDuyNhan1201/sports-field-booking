package org.jakartaee5g23.sportsfieldbooking.mappers;

import java.util.Collections;
import java.util.stream.Collectors;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.booking.NewBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {
        BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

        Booking toBooking(NewBookingRequest dto);

        BookingResponse toBookingResponse(Booking entity);

        @AfterMapping
        default void customizeDto(Booking entity, @MappingTarget BookingResponse dto) {
                dto.setMUser(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
                dto.setCreatedAt(entity.getCreatedAt());
//                dto.setMSportField(
//                        SportsFieldMapper.INSTANCE
//                                .toSportsFieldResponse(entity.getFieldAvailability().getSportsField()));
//                dto.setMFieldAvailability(
//                        FieldAvailabilityMapper.INSTANCE
//                                .toFieldAvailabilityResponse(entity.getFieldAvailability()));
                if (entity.getBookingItems() != null) {
                        dto.setMBookingItems(entity.getBookingItems().stream()
                                .map(BookingItemMapper.INSTANCE::toBookingItemResponse)
                                .collect(Collectors.toList()));
                } else {
                        dto.setMBookingItems(Collections.emptyList());
                }
        }
}