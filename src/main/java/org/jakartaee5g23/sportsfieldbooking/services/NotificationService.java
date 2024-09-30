package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    Page<Notification> findByUser(User user, int offset, int limit);

}