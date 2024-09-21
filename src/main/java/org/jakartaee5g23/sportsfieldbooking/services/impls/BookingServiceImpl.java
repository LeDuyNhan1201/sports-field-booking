package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.CancelBookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.CancelBookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingNotFoundException;
import org.jakartaee5g23.sportsfieldbooking.repositories.NotificationRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
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

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional
    public BookingResponse getBookingConfirmation(BookingRequest request) {
        if (request.isConfirmed()) {
            User user = userRepository.findById(request.idUser())
                    .orElseThrow(() -> new BookingException(BookingErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
            SportField sportField = sportFieldRepository.findById(request.idSportField()).orElseThrow(
                    () -> new BookingException(BookingErrorCode.SPORTFIELD_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (user.getStatus() == UserStatus.BANNED) {
                throw new BookingException(BookingErrorCode.USER_BANNED, HttpStatus.NOT_FOUND);
            } else if (sportField.getStatus() != SportFieldStatus.NONE) {
                throw new BookingException(BookingErrorCode.SPORTFIELD_NONE, HttpStatus.NOT_FOUND);
            }
            Order order = Order.builder()
                    .startTime(request.startTime())
                    .bookingHours(request.bookingHours())
                    .endTime(request.endTime())
                    .status(OrderStatus.PENDING)
                    .user(user)
                    .sportField(sportField)
                    .build();

            Order createOrder = createBooking(order);

            // check if createOrder is created
            Order existOrder = orderRepository.findById(createOrder.getId())
                    .orElseThrow(() -> new BookingException(BookingErrorCode.SEND_MAIL_FAILED, HttpStatus.NOT_FOUND));

            sportField.setStatus(SportFieldStatus.PRE_ORDER);
            Notification notification = Notification.builder()
                    .user(user)
                    .order(existOrder)
                    .type(NotificationType.INFO)
                    .message(getLocalizedMessage("booking_confirmed"))
                    .created(new Date())
                    .build();

            notificationRepository.save(notification);

            return existOrder != null
                    ? new BookingResponse(HttpStatus.OK.value(), getLocalizedMessage("booking_success"))
                    : new BookingResponse(HttpStatus.BAD_REQUEST.value(), getLocalizedMessage("booking_failed"));
        } else {
            return new BookingResponse(HttpStatus.BAD_REQUEST.value(), getLocalizedMessage("booking_failed"));
        }
    }

    @Override
    public Order createBooking(Order order) {
        return orderRepository.save(order);
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
    public List<BookingResponse> getUpcomingBookings() {
        List<Order> upcomingOrders = orderRepository.findUpcomingBookings();
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
        return "Booking for " + order.getSportField().getName() + " by " + order.getUser().getUsername();
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
            order.setStartTime(newBookingRequest.startTime());
            order.setEndTime(newBookingRequest.endTime());
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
