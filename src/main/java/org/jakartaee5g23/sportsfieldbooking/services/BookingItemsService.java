package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingItemsService {

    BookingItem findById(String id);

    BookingItem create(BookingItem item);

    List<BookingItem> findBySportsFieldId(String sportsFieldId);

    void updateBookingItemStatus();
}
