package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.springframework.data.domain.Page;

public interface BookingService {

    Booking findById(String id);

    Booking create(Booking request);

    Page<Booking> findAll(int offset, int limit);

    Page<Booking> findAllByUser(User user, int offset, int limit);

    Page<Booking> findAllByStatus(BookingStatus status, int offset, int limit);

    Page<Booking> getUpcomingBookings(String userId, int offset, int limit);

    Page<Booking> findByField(SportField sportField, Date beginDate, Date endDate, int offset, int limit);

    Booking updateStatus(String bookingId, BookingStatus status);

}
