package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import java.util.*;

public interface NotificationService {
    List<Notification> getNotificationsByUserId(String userId);
}