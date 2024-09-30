package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.repositories.FieldAvailabilityRepository;
import org.jakartaee5g23.sportsfieldbooking.services.FieldAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
        FieldAvailability fieldAvailability = fieldAvailabilityRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field availability"));

        if (!fieldAvailability.getIsAvailable())
            throw new BookingException(BookingErrorCode.FIELD_AVAILABILITY_ORDERED, HttpStatus.UNPROCESSABLE_ENTITY);

        return fieldAvailability;
    }

    @Override
    public boolean isAlreadyOrdered(Booking booking) {
        return fieldAvailabilityRepository.findByBooking(booking).isPresent();
    }

    @Override
    public FieldAvailability create(FieldAvailability request) {
        return fieldAvailabilityRepository.save(request);
    }

    @Override
    @Scheduled(fixedRate = 30000) // 30s
    public void updateFieldAvailabilityStatus() {
        Date currentTime = new Date();

        List<FieldAvailability> fieldAvailabilityList = fieldAvailabilityRepository.findAll();

        for (FieldAvailability fieldAvailability : fieldAvailabilityList) {
            if (fieldAvailability.getEndTime().before(currentTime)) {
                fieldAvailability.setIsAvailable(false);
                fieldAvailabilityRepository.save(fieldAvailability);
            }
        }
    }

}