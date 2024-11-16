package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.entities.FieldAvailability;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingItemRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingItemsServiceImpl implements BookingItemsService {

    BookingItemRepository bookingItemsRepository;

    @Override
    public BookingItem findById(String id) {
        return bookingItemsRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Booking Items"));
    }

    @Override
    public BookingItem create(BookingItem item) {
        return bookingItemsRepository.save(item);
    }

    @Override
    @Scheduled(fixedRate = 30000) // 30s
    public void updateBookingItemStatus() {
        Date currentTime = new Date();
        List<BookingItem> bookingItemList = bookingItemsRepository.findAll();

        for (BookingItem bookingItem : bookingItemList) {
            if (bookingItem.getEndTime().before(currentTime)) {
                bookingItem.setStatus(BookingItemStatus.COMPLETE);
                bookingItemsRepository.save(bookingItem);
            }
        }
    }

}
