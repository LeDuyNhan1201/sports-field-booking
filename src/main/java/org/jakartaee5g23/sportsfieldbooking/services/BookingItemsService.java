package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.BookingItems;
import org.springframework.stereotype.Service;

@Service
public interface BookingItemsService {
    BookingItems findById(String id);

}
