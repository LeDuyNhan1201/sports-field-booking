package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;

public interface BookingService {
    BookingResponse getBookingConfirmation(BookingRequest request);

    Order createBooking(Order order);

    void cancelBooking(String orderID);
}
