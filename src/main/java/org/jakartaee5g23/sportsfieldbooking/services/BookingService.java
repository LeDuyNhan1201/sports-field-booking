package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.CancelBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.CancelBookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;

public interface BookingService {
    BookingResponse getBookingConfirmation(BookingRequest request);

    Order createBooking(Order order);

    CancelBookingResponse cancelBooking(CancelBookingRequest request);

    List<BookingResponse> getUpcomingBookings();
}
