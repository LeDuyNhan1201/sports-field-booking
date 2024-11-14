package org.jakartaee5g23.sportsfieldbooking.controllers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.NotificationResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.mappers.NotificationMapper;
import org.jakartaee5g23.sportsfieldbooking.services.NotificationService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getUserIdFromContext;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Notification APIs")
public class NotificationController {

    NotificationService notificationService;

    UserService userService;

    NotificationMapper notificationMapper = NotificationMapper.INSTANCE;

    @Operation(summary = "View my notification", description = "Get list of notification", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<PaginateResponse<NotificationResponse>> getMyNotification(
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "100") String limit) {
        User current = userService.findById(getUserIdFromContext());
        Page<Notification> notifications = notificationService.findByUser(current, Integer.parseInt(offset),
                Integer.parseInt(limit));
        return ResponseEntity.status(HttpStatus.OK).body(PaginateResponse.<NotificationResponse>builder()
                .items(notifications.stream().map(notificationMapper::toNotificationResponse).toList())
                .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit),
                        notifications.getTotalElements()))
                .build());
    }

    @Operation(summary = "Read notification", description = "Read notification", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{id}/read")
    public ResponseEntity<Void> readNotification(@PathVariable String id) {
        User current = userService.findById(getUserIdFromContext());
        notificationService.readNotification(current, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Read all notifications", description = "Mark all notifications as read for the current user", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/read-all")
    public ResponseEntity<Void> readAllNotifications() {
        User current = userService.findById(getUserIdFromContext());
        notificationService.readAllNotifications(current);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}