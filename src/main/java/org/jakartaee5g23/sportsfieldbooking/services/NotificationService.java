package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    Notification findById(Long id);

    Page<Notification> findByUser(User user, int offset, int limit);

    void readNotification(User user, String notificationId);

    void readAllNotifications(User user);

    void createNotification(User user, String booking, NotificationType type, String message);
}