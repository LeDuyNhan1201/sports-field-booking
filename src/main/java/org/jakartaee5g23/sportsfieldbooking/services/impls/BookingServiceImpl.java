package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.List;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.jakartaee5g23.sportsfieldbooking.enums.BookingStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.repositories.*;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;

    NotificationRepository notificationRepository;

    @Override
    public Booking findById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Booking"));
    }

    @Override
    @Transactional
    public Booking create(Booking request) {
        User user = request.getUser();
//        FieldAvailability fieldAvailability = request.getFieldAvailability();
//        if (!fieldAvailability.getSportsField().getStatus().equals(SportsFieldStatus.OPEN))
//            throw new BookingException(BookingErrorCode.SPORT_FIELD_NOT_AVAILABLE, HttpStatus.UNPROCESSABLE_ENTITY);

        if (user.getStatus() == UserStatus.BANNED)
            throw new BookingException(BookingErrorCode.USER_BANNED, HttpStatus.UNPROCESSABLE_ENTITY);

        Booking booking = Booking.builder()
//                .fieldAvailability(fieldAvailability)
                .updatedAt(new Date())
                .status(BookingStatus.PENDING)
                .user(user)
                .build();

        Booking createBooking = bookingRepository.save(booking);

        Notification notification = Notification.builder()
                .user(user)
                .booking(createBooking)
                .type(NotificationType.ORDER_STATUS_UPDATE)
                .message(getLocalizedMessage("booking_confirmed"))
                .build();

        notificationRepository.save(notification);

        return createBooking;

    }

    @Override
    public Page<Booking> findAll(int offset, int limit) {
        return bookingRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Booking> findAllByUser(User user, int offset, int limit) {
        return bookingRepository.findAllByUser(user, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Booking> findAllByStatus(BookingStatus status, int offset, int limit) {
        return bookingRepository.findAllByStatus(status,
                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

//    @Override
//    public Page<Booking> getUpcomingBookings(String userId, int offset, int limit) {
//        return bookingRepository.findUpcomingBookingsByUserId(userId,
//                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
//    }

//    @Override
//    public Page<Booking> findByField(SportsField sportsField, Date beginDate, Date endDate, int offset, int limit) {
//        return bookingRepository.findBySportFieldIdAndStartTimeBetween(sportsField, beginDate, endDate,
//                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
//    }

    @Override
    public Booking updateStatus(String bookingId, BookingStatus status) {
        Booking booking = findById(bookingId);
        switch (status) {
            case CANCELED:
                if (booking.getStatus().equals(BookingStatus.PENDING))
                    booking.setStatus(BookingStatus.CANCELED);
                else
                    throw new BookingException(BookingErrorCode.CANCEL_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);
                break;

            case RESCHEDULED, REFUND_REQUESTED:
                booking.setStatus(status);
                break;

            case ACCEPTED:
                booking.setStatus(BookingStatus.ACCEPTED);
                break;

            case PENDING:
                booking.setStatus(BookingStatus.PENDING);
                break;

            case REJECTED:
                booking.setStatus(BookingStatus.REJECTED);
                break;
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Page<Booking> findBookingHistoryByUser(User user, List<BookingStatus> statuses, int offset, int limit) {
        return bookingRepository.findAllByUserAndStatusIn(user, statuses,
                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Booking cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

        return bookingRepository.findBookingsByDateRange(startDate, endDate);
    }

    public List<Booking> getBookingsForPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

        return bookingRepository.findBookingsByDateRange(startDate, endDate);
    }

    public List<Booking> getBookingsForCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfWeek = calendar.getTime();

        return bookingRepository.findBookingsByDateRange(startOfWeek, endOfWeek);
    }

    public List<Booking> getBookingsForPreviousWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1); // back 1 week from current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfWeek = calendar.getTime();

        return bookingRepository.findBookingsByDateRange(startOfWeek, endOfWeek);
    }
}
