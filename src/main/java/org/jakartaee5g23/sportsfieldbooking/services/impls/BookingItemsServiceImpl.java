package org.jakartaee5g23.sportsfieldbooking.services.impls;

import org.jakartaee5g23.sportsfieldbooking.entities.BookingItems;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingItemRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingItemsServiceImpl implements BookingItemsService {

    @Autowired
    private BookingItemRepository bookingItemsRepository;

    @Override
    public BookingItems findById(String id) {
        return bookingItemsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BookingItems not found with id: " + id));
    }
}
