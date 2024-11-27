package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

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
        createBooking.setCreatedAt(new Date());

        Notification notification = Notification.builder()
                .user(user)
                .booking(createBooking)
                .type(NotificationType.ORDER_STATUS_UPDATE)
                .message(getLocalizedMessage("booking_confirmed"))
                .build();

        notificationRepository.save(notification);

        return bookingRepository.save(createBooking);

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

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<Booking> searchBookings(String keyword, BookingStatus status, Date startDate, Date endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return bookingRepository.searchBookings(keyword, status, startDate, endDate, pageable);
    }

    @Override
    public List<Booking> getBookingsForCurrentMonth(Date date, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, month, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfMonth = calendar.getTime();

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfMonth, endOfMonth);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }

    @Override
    public List<Booking> getBookingsForPreviousMonth(Date date, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, month, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfMonth = calendar.getTime();

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfMonth, endOfMonth);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }

    public List<Booking> getBookingsForCurrentWeek(String userId) {
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

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfWeek, endOfWeek);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }

    public List<Booking> getBookingsForPreviousWeek(String userId) {
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

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfWeek, endOfWeek);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }

    @Override
    public List<Booking> getBookingsFromYear(Date fromDate, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startOfYear = calendar.getTime();

        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endOfYear = calendar.getTime();

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfYear, endOfYear);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }
    @Override
    public List<Booking> getBookingsToYear(Date toDate, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startOfYear = calendar.getTime();

        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endOfYear = calendar.getTime();

        List<Booking> bookings = bookingRepository.findBookingsByDateRange(startOfYear, endOfYear);

        return bookings.stream()
                .map(booking -> {
                    List<BookingItem> filteredItems = booking.getBookingItems().stream()
                            .filter(item -> item.getSportsField().getUser().getId().equals(userId))
                            .toList();
                    booking.setBookingItems(filteredItems);

                    ZonedDateTime bangkokTime = booking.getCreatedAt()
                            .toInstant()
                            .atZone(ZoneId.of("Asia/Bangkok"))
                            .plusHours(7);

                    booking.setCreatedAt(Date.from(bangkokTime.toInstant()));

                    return booking;
                })
                .filter(booking -> !booking.getBookingItems().isEmpty())
                .toList();
    }


    public List<String> getBookingStatus() {
        return Arrays.stream(BookingStatus.values())
                .map(Enum::name)
                .toList();
    }

    public Page<Booking> findTopOrdersByFieldOwner(String fieldOwnerId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);

        return bookingRepository.findBookingsByFieldOwner(fieldOwnerId, pageable);
    }
}
