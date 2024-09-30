package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.NotificationResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponse toNotificationResponse(Notification entity);
    @AfterMapping
    default void customizeDto(Notification entity, @MappingTarget NotificationResponse dto) {
        dto.setMBooking(BookingMapper.INSTANCE.toBookingResponse(entity.getBooking()));
    }

}