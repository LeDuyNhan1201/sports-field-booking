package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItems;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingItemRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingItemsServiceImpl implements BookingItemsService {

    BookingItemRepository bookingItemsRepository;

    @Override
    public BookingItems findById(String id) {
        return bookingItemsRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Booking Items"));
    }

}
