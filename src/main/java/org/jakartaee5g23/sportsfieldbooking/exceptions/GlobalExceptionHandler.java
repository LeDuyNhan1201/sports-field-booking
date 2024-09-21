package org.jakartaee5g23.sportsfieldbooking.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.CommonResponse;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.booking.BookingException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.order.OrderException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.payment.PaymentException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle exceptions that are not caught by other handlers
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<CommonResponse<?>> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        return ResponseEntity.badRequest().body(CommonResponse.builder()
                .message(getLocalizedMessage("uncategorized"))
                .build());
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<CommonResponse<?>> handlingAuthorizationDeniedException(AuthorizationDeniedException exception) {
        log.error("Authorization Denied Exception: ", exception);
        return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
                .message(getLocalizedMessage("not_have_permission"))
                .build());
    }

    // Handle exceptions about messages that are not found
    @ExceptionHandler(value = NoSuchMessageException.class)
    ResponseEntity<CommonResponse<?>> handlingNoSuchMessageException(NoSuchMessageException exception) {
        log.error("Message Not Found Exception: ", exception);
        return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
                .message(exception.getMessage())
                .build());
    }


    // booking exceptions
    @ExceptionHandler(value = BookingException.class)
    ResponseEntity<CommonResponse<?>> handlingBookingException(BookingException exception) {
        log.error("Booking error: ", exception);
        return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
                .message(exception.getMessage())
                .build());
    }

    // payment exceptions
    @ExceptionHandler(value = PaymentException.class)
    ResponseEntity<CommonResponse<?>> handlingPaymentException(PaymentException exception) {
        log.error("Payment error: ", exception);
        return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
                .message(exception.getMessage())
                .build());
    }

    // order exceptions
    @ExceptionHandler(value = OrderException.class)
    ResponseEntity<CommonResponse<?>> handlingOrderException(OrderException exception) {
        log.error("Order error: ", exception);
        return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
                .message(exception.getMessage())
                .build());
    }


    // Handle authentication exceptions
    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<CommonResponse<?>> handlingAuthenticationExceptions(AuthenticationException exception) {
        log.error("Authentication Exception: {}", exception.toString());
        AuthenticationErrorCode errorCode = exception.getAuthenticationErrorCode();
        return ResponseEntity.status(exception.getHttpStatus()).body(CommonResponse.builder()
                .errorCode(errorCode.getCode())
                .message((exception.getMoreInfo() != null)
                        ? getLocalizedMessage(errorCode.getMessage(), exception.getMoreInfo())
                        : getLocalizedMessage(errorCode.getMessage()))
                .errors(switch (exception.getAuthenticationErrorCode()) {
                    case VALIDATION_ERROR -> new HashMap<>(Map.of(
                            "email", getLocalizedMessage(VALIDATION_ERROR.getMessage()),
                            "password", getLocalizedMessage(VALIDATION_ERROR.getMessage())));

                    case EXPIRED_PASSWORD ->
                            new HashMap<>(Map.of("password", getLocalizedMessage(EXPIRED_PASSWORD.getMessage())));

                    case TOKEN_INVALID ->
                            new HashMap<>(Map.of("token", getLocalizedMessage(TOKEN_INVALID.getMessage())));

                    case WRONG_PASSWORD ->
                            new HashMap<>(Map.of("password", getLocalizedMessage(WRONG_PASSWORD.getMessage())));

                    case PASSWORD_MIS_MATCH ->
                            new HashMap<>(Map.of("password", getLocalizedMessage(PASSWORD_MIS_MATCH.getMessage())));

                    case EMAIL_ALREADY_IN_USE ->
                            new HashMap<>(Map.of("email", getLocalizedMessage(EMAIL_ALREADY_IN_USE.getMessage())));

                    case WEAK_PASSWORD ->
                            new HashMap<>(Map.of("password", getLocalizedMessage(WEAK_PASSWORD.getMessage())));

                    case INVALID_EMAIL ->
                            new HashMap<>(Map.of("email", getLocalizedMessage(INVALID_EMAIL.getMessage())));

                    case TERMS_NOT_ACCEPTED ->
                            new HashMap<>(Map.of("termsAccepted", getLocalizedMessage(TERMS_NOT_ACCEPTED.getMessage())));

                    case CODE_INVALID ->
                            new HashMap<>(Map.of("code", getLocalizedMessage(CODE_INVALID.getMessage())));

                    default -> null;
                })
                .build());
    }

    // Handle file storage exceptions
    @ExceptionHandler(value = FileStorageException.class)
    ResponseEntity<CommonResponse<?>> handlingFileStorageExceptions(FileStorageException exception) {
        log.error("File Storage Exception: {}", exception.toString());
        FileStorageErrorCode errorCode = exception.getFileStorageErrorCode();
        return ResponseEntity.status(exception.getHttpStatus()).body(CommonResponse.builder()
                .errorCode(errorCode.getCode())
                .message((exception.getMoreInfo() != null)
                        ? getLocalizedMessage(errorCode.getMessage(), exception.getMoreInfo())
                        : getLocalizedMessage(errorCode.getMessage()))
                .build());
    }

    // Handle exceptions that request data is invalid (validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>>
    handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        log.error("Validation Exception: ", e);
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String field = ((FieldError) error).getField();
                    errors.put(field, getLocalizedMessage(error.getDefaultMessage()));
                });

        return ResponseEntity.status(BAD_REQUEST).body(
                CommonResponse.builder()
                        .errorCode(VALIDATION_ERROR.getCode())
                        .message(getLocalizedMessage(VALIDATION_ERROR.getMessage()))
                        .errors(errors)
                        .build()
        );
    }

}