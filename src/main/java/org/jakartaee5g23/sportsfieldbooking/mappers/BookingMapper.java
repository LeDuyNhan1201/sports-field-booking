package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.order.NewBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.order.BookingResponse;
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
        dto.setMUser(UserMapper.INSTANCE.toUserInfoResponse(entity.getUser()));
        dto.setMSportField(SportFieldMapper.INSTANCE.toSportFieldResponse(entity.getSportField()));
    }

}