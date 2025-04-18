package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingItemsService {

    BookingItem findById(String id);

    BookingItem create(BookingItem item);

    List<BookingItem> findBySportsFieldId(String sportsFieldId);

    Page<BookingItem> findAll(int offset, int limit);

    Page<BookingItem> findBookingItemsByFieldOwner(String fieldOwnerId, int offset, int limit);

    public BookingItem updateStatus(String bookingItemId, BookingItemStatus status);
    void updateBookingItemStatus();
}
