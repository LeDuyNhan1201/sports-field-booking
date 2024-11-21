package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.Date;

import java.util.List;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.springframework.data.domain.Page;

public interface BookingService {

    Booking findById(String id);

    Booking create(Booking request);

    Page<Booking> findAll(int offset, int limit);

    Page<Booking> findAllByUser(User user, int offset, int limit);

    Page<Booking> findAllByStatus(BookingStatus status, int offset, int limit);

//    Page<Booking> getUpcomingBookings(String userId, int offset, int limit);

//    Page<Booking> findByField(SportsField sportsField, Date beginDate, Date endDate, int offset, int limit);

    Booking updateStatus(String bookingId, BookingStatus status);

    Page<Booking> findBookingHistoryByUser(User user, List<BookingStatus> statuses, int offset, int limit);

    Booking cancelBooking(String bookingId);

    void deleteBooking(String id);

    Page<Booking> searchBookings(String keyword, BookingStatus status, Date startDate, Date endDate, int page, int size);

    List<Booking> getBookingsForCurrentMonth();

    List<Booking> getBookingsForPreviousMonth();

    List<Booking> getBookingsForCurrentWeek();

    List<Booking> getBookingsForPreviousWeek();
}
