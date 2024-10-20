package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.BookingItem;
import org.springframework.stereotype.Service;

@Service
public interface BookingItemsService {

    BookingItem findById(String id);

}
