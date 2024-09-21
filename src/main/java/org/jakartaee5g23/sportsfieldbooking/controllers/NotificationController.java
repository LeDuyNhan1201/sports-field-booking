package org.jakartaee5g23.sportsfieldbooking.controllers;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.NotificationResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Notification APIs")
public class NotificationController {
    NotificationService notificationService;

    @Operation(summary = "View my notification", description = "Get list of notification", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<NotificationResponse> getListNotificationByUser(@RequestParam("userId") String userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new NotificationResponse(notifications));
    }
}