package org.jakartaee5g23.sportsfieldbooking.services.impls;

import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.repositories.NotificationRepository;
import org.jakartaee5g23.sportsfieldbooking.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;

    @Override
    public Page<Notification> findByUser(User user, int offset, int limit) {
        return notificationRepository.findByUser(user, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

}