package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.BookingRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.jakartaee5g23.sportsfieldbooking.entities.Payment;
import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.OrderStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.repositories.OrderRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.PaymentRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookingServiceImpl implements BookingService {
    OrderRepository orderRepository;

    UserRepository userRepository;

    SportFieldRepository sportFieldRepository;

    PaymentRepository paymentRepository;


    @Override
    @Transactional
    public BookingResponse getBookingConfirmation(BookingRequest request) {
        if (request.isConfirmed()) {
            User user = userRepository.findById(request.idUser()).orElseThrow(() -> new BookingException(BookingErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
            SportField sportField = sportFieldRepository.findById(request.idSportField()).orElseThrow(() -> new BookingException(BookingErrorCode.SPORTFIELD_NOT_FOUND, HttpStatus.NOT_FOUND));
            Payment payment = paymentRepository.findById(request.idUser()).orElseThrow(() -> new BookingException(BookingErrorCode.PAYMENT_NOT_FOUND, HttpStatus.NOT_FOUND));

            Order order = Order.builder()
                    .startTime(request.startTime())
                    .bookingHours(request.bookingHours())
                    .endTime(request.endTime())
                    .status(OrderStatus.PENDING)
                    .user(user)
                    .sportField(sportField)
                    .payment(payment)
                    .build();

            Order createOrder = createBooking(order);

            // check if createOrder is created
            Order existOrder = orderRepository.findById(createOrder.getId())
                    .orElseThrow(() -> new BookingException(BookingErrorCode.SEND_MAIL_FAILED, HttpStatus.NOT_FOUND));

            return new BookingResponse(getLocalizedMessage("booking_success"));
        }else {
            return new BookingResponse(getLocalizedMessage("booking_failed"));
        }
    }

    @Override
    public Order createBooking(Order order) {
        return orderRepository.save(order);
    }


    @Override
    public void cancelBooking(String orderID) {
        Order order = orderRepository.findById(orderID).orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        if (order != null) {
            order.setStatus(OrderStatus.CANCELED);
        }
    }
}
