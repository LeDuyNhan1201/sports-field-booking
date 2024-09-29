package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.CancelBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.CancelBookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingNotFoundException;
import org.jakartaee5g23.sportsfieldbooking.repositories.*;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingServiceImpl implements BookingService {
    OrderRepository orderRepository;

    UserRepository userRepository;

    SportFieldRepository sportFieldRepository;

    FieldAvailabilityRepository fieldAvailabilityRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        User user = userRepository.findById(request.idUser())
                .orElseThrow(() -> new BookingException(BookingErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        SportField sportField = sportFieldRepository.findById(request.idSportField()).orElseThrow(
                () -> new BookingException(BookingErrorCode.SPORTFIELD_NOT_FOUND, HttpStatus.NOT_FOUND));
        FieldAvailability fieldAvailability = fieldAvailabilityRepository.findById(request.fieldAvaibilityID())
                .orElseThrow(() -> new BookingException(BookingErrorCode.FIELD_AVAIBILITY_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!fieldAvailability.getIsAvailable()) {
            throw new BookingException(BookingErrorCode.FIELD_AVAIBILITY_ORDERED, HttpStatus.BAD_REQUEST);
        }

        if (!sportField.getStatus().equals(SportFieldStatus.OPEN)) {
            throw new BookingException(BookingErrorCode.SPORTFIELD_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
        }

        if (user.getStatus() == UserStatus.BANNED) {
            throw new BookingException(BookingErrorCode.USER_BANNED, HttpStatus.NOT_FOUND);
        }

        fieldAvailability.setIsAvailable(false);

        Order order = Order.builder()
                .fieldAvailability(fieldAvailability)
                .status(OrderStatus.PENDING)
                .orderDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .user(user)
//                .sportField(sportField)
                .build();

        Order createOrder = orderRepository.save(order);

        // check if createOrder is created
        Order existOrder = orderRepository.findById(createOrder.getId())
                .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        Notification notification = Notification.builder()
                .user(user)
                .order(existOrder)
                .type(NotificationType.ORDER_STATUS_UPDATE)
                .message(getLocalizedMessage("booking_confirmed"))
                .created(new Date())
                .build();

        notificationRepository.save(notification);

        return existOrder != null
                ? new BookingResponse(HttpStatus.OK.value(), getLocalizedMessage("booking_success"))
                : new BookingResponse(HttpStatus.BAD_REQUEST.value(), getLocalizedMessage("booking_failed"));

    }

    @Override
    public CancelBookingResponse cancelBooking(CancelBookingRequest request) {
        Order order = orderRepository.findById(request.orderID())
                .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        if (order != null && order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            return new CancelBookingResponse(getLocalizedMessage("cancel_success"));
        }
        return new CancelBookingResponse(getLocalizedMessage("cancel_failed"));
    }

    @Override
    public List<BookingResponse> getUpcomingBookingsByUserId(String userId) {
        List<Order> upcomingOrders = orderRepository.findUpcomingBookingsByUserId(userId);
        return upcomingOrders.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse convertToBookingResponse(Order order) {
        int errorCode = deriveErrorCode(order);
        String message = deriveMessage(order);
        return new BookingResponse(errorCode, message);
    }

    private int deriveErrorCode(Order order) {

        return 0;
    }

    private String deriveMessage(Order order) {
        Optional<SportField> optionalSportField = sportFieldRepository.findById(order.getUser().getId());

        if (optionalSportField.isPresent()) {
            SportField sportField = optionalSportField.get();
            return "Booking for " + sportField.getName() + " by " + order.getUser().getUsername();
        } else {
            throw new RuntimeException("SportField not found for id: " + order.getUser().getId());
        }
    }


    @Override
    public List<BookingResponse> getBookingHistory(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse rescheduleBooking(String bookingId, BookingRequest newBookingRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(bookingId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
//            order.setStartTime(newBookingRequest.startTime());
//            order.setEndTime(newBookingRequest.endTime());
            order.setStatus(OrderStatus.RESCHEDULED);
            orderRepository.save(order);
            return convertToBookingResponse(order);
        } else {
            throw new BookingNotFoundException("Booking not found with id: " + bookingId);
        }
    }

    @Override
    public BookingResponse requestRefund(String bookingId) {
        Optional<Order> optionalOrder = orderRepository.findById(bookingId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.REFUND_REQUESTED);
            orderRepository.save(order);
            return convertToBookingResponse(order);
        } else {
            throw new BookingNotFoundException("Booking not found with id: " + bookingId);
        }
    }
}
