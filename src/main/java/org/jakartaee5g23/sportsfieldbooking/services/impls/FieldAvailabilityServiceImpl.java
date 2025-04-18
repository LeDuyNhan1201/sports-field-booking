package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.FieldAvailabilityStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldException;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FieldAvailabilityServiceImpl implements FieldAvailabilityService {

    FieldAvailabilityRepository fieldAvailabilityRepository;

    @Override
    public FieldAvailability findById(String id) {

//        if (!fieldAvailability.getIsAvailable())
//            throw new BookingException(BookingErrorCode.FIELD_AVAILABILITY_ORDERED, HttpStatus.UNPROCESSABLE_ENTITY);

        return fieldAvailabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field availability"));
    }

//    @Override
//    public boolean isAlreadyOrdered(Booking booking) {
//        return fieldAvailabilityRepository.findByBooking(booking).isPresent();
//    }

    @Override
    public FieldAvailability create(FieldAvailability request, boolean isConfirmed) {
        Date openingTime = request.getStartTime();
        Date closingTime = request.getEndTime();
        
        FieldAvailability createFieldAvailability = FieldAvailability.builder()
            .startTime(openingTime)
            .endTime(closingTime)
            .price(request.getPrice())
                .status(FieldAvailabilityStatus.AVAILABLE)
            .sportsField(request.getSportsField())
            .build();
        return fieldAvailabilityRepository.save(createFieldAvailability);
    }


    @Override
    public void update(FieldAvailability fieldAvailability) {
        fieldAvailabilityRepository.save(fieldAvailability);
    }

    @Override
    public FieldAvailability updateStatus(String id, FieldAvailabilityStatus status) {
        FieldAvailability fieldAvailability = fieldAvailabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field availability"));

        fieldAvailability.setStatus(status);

        return fieldAvailabilityRepository.save(fieldAvailability);
    }

}
