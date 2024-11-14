package org.jakartaee5g23.sportsfieldbooking.services.impls;

import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.Notification;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.NotificationType;
import org.jakartaee5g23.sportsfieldbooking.repositories.NotificationRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingRepository;
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
    BookingRepository bookingRepository;

    @Override
    public Page<Notification> findByUser(User user, int offset, int limit) {
        return notificationRepository.findByUser(user,
                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void readNotification(User user, String notificationId) {
        Notification notification = notificationRepository.findById(notificationId);
        if (notification != null && notification.getUser().getId().equals(user.getId())) {
            notificationRepository.readNotification(notification.getId());
        }
    }

    @Override
    public void readAllNotifications(User user) {
        notificationRepository.readAllNotifications(user);
    }

    @Override
    public void createNotification(User user, String bookingId, NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        notification.setBooking(booking);
        notification.setType(type);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}