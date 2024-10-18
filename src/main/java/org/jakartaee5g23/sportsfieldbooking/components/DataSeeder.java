package org.jakartaee5g23.sportsfieldbooking.components;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.*;
import org.jakartaee5g23.sportsfieldbooking.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.getRandomEnum;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataSeeder {

    RoleRepository roleRepository;
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    SportsFieldRepository sportFieldRepository;
    CategoryRepository categoryRepository;
    PaymentRepository paymentRepository;
    BookingRepository bookingRepository;
    ReviewRepository reviewRepository;
    PasswordEncoder passwordEncoder;
    NotificationRepository notificationRepository;
    PromotionRepository promotionRepository;
    StatisticRepository statisticRepository;
    FieldAvailabilityRepository fieldAvailabilityRepository;
    BookingItemRepository bookingItemRepository;

    Faker faker = new Faker();

    @PostConstruct
    @Transactional
    public void seed() {
        seedRoles();
        seedUsers();
        seedUserRole();
        seedCategories();
        seedSportFields();
        seedReviews();
        seedFieldAvailabilities();
        seedBookings();
        seedPayments();
        seedNotifications();
        seedPromotions();
        seedStatistics();
        seedBookingItems();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role admin = Role.builder().name("ADMIN").build();
            Role customer = Role.builder().name("CUSTOMER").build();
            Role fieldOwner = Role.builder().name("FIELD_OWNER").build();

            roleRepository.saveAll(List.of(customer, fieldOwner, admin));
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            IntStream.range(0, 20).forEach(index -> {
                User user = User.builder()
                        .username(faker.name().username())
                        .password(passwordEncoder.encode("123456"))
                        .email(faker.internet().emailAddress())
                        .firstName(faker.name().firstName())
                        .middleName(faker.name().nameWithMiddle())
                        .lastName(faker.name().lastName())
                        .mobileNumber(faker.phoneNumber().subscriberNumber(10))
                        .birthdate(LocalDate.now().minusYears(21))
                        .bio(faker.lorem().sentence(20))
                        .gender(getRandomEnum(Gender.class))
                        .avatar(null)
                        .status(getRandomEnum(UserStatus.class))
                        .isActivated(true)
                        .build();
                userRepository.save(user);
            });
        }
    }

    private void seedUserRole() {
        if (userRoleRepository.count() == 0) {
            List<Role> roles = roleRepository.findAll();
            List<User> users = userRepository.findAll();

            users.forEach(user -> {
                UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(roles.get(faker.number().numberBetween(0, roles.size())))
                        .build();

                userRoleRepository.save(userRole);
            });
        }
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            IntStream.range(0, 5).forEach(index -> {
                Category category = Category.builder().name(faker.team().sport()).build();
                categoryRepository.save(category);
            });
        }
    }

    private void seedSportFields() {
        if (sportFieldRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Category> categories = categoryRepository.findAll();

            IntStream.range(0, 20).forEach(index -> {
                SportsField field = SportsField.builder()
                        .name(faker.team().name())
                        .location(faker.address().fullAddress())
                        .opacity(faker.number().numberBetween(1, 100))
                        .openingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .closingTime(faker.date().future(365 * 5, TimeUnit.DAYS)) // Updated to future
                        .category(categories.get(faker.number().numberBetween(0, categories.size())))
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .rating(faker.number().randomDouble(2, 0, 5))
                        .status(getRandomEnum(SportsFieldStatus.class))
                        .build();

                sportFieldRepository.save(field);
            });
        }
    }

    private void seedFieldAvailabilities() {
        if (fieldAvailabilityRepository.count() == 0) {
            List<SportsField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 30).forEach(index -> {
                SportsField field = fields.get(faker.number().numberBetween(0, fields.size()));
                // Create a date that is available in the next 30 days
                Date availableDate = faker.date().future(30, TimeUnit.DAYS);
//
//                // Generate start time between 8:00 AM and 8:00 PM
                LocalDate localDate = availableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date startTime = Date.from(localDate.atTime(faker.number().numberBetween(8, 20), 0)
                        .atZone(ZoneId.systemDefault()).toInstant());

                // Generate end time between 1 to 12 hours after start time
                Date endTime = new Date(startTime.getTime() + (long) faker.number().numberBetween(1, 12) * 60 * 60 * 1000);

                FieldAvailability availability = FieldAvailability.builder()
                        .sportsField(field)
                        .availableDate(availableDate)
                        .startTime(startTime)
                        .pricePerHour(faker.number().randomDouble(2, 10, 100))
                        .endTime(endTime)
                        .isAvailable(faker.bool().bool())
                        .build();

                fieldAvailabilityRepository.save(availability);
            });
        }
    }

    private void seedBookings() {
        if (bookingRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<FieldAvailability> availabilities = fieldAvailabilityRepository.findAll();

            IntStream.range(0, 20).forEach(index -> {
                FieldAvailability availability = availabilities.get(index);

                Booking booking = Booking.builder()
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .fieldAvailability(availability)
                        .status(getRandomEnum(BookingStatus.class))
                        .build();

                bookingRepository.save(booking);
            });
        }
    }

    private void seedPayments() {
        if (paymentRepository.count() == 0) {
            List<Booking> bookings = bookingRepository.findAll();
            List<FieldAvailability> fieldAvailabilities = fieldAvailabilityRepository.findAll();

            IntStream.range(0, 20).forEach(i -> {
                Booking booking = bookings.get(i);
                FieldAvailability fieldAvailability = fieldAvailabilities.get(i);

                LocalDateTime startTime = booking.getFieldAvailability().getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = booking.getFieldAvailability().getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                int hours = (int) Duration.between(startTime, endTime).toHours();

                Payment payment = Payment.builder()
                        .method(getRandomEnum(PaymentMethod.class))
                        .price(fieldAvailability.getPricePerHour() * hours)
                        .booking(booking)
                        .status(getRandomEnum(PaymentStatus.class))
                        .build();

                paymentRepository.save(payment);
            });
        }
    }


    private void seedReviews() {
        if (reviewRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<SportsField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 20).forEach(index -> {
                Review review = Review.builder()
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .sportsField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .comment(faker.lorem().sentence(15))
                        .build();

                reviewRepository.save(review);
            });
        }
    }

    private void seedNotifications() {
        if (notificationRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Booking> bookings = bookingRepository.findAll();

            IntStream.range(0, 20).forEach(index -> {
                Notification notification = Notification.builder()
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .booking(bookings.get(faker.number().numberBetween(0, bookings.size())))
                        .type(getRandomEnum(NotificationType.class))
                        .message(faker.lorem().sentence(10))
                        .build();

                notificationRepository.save(notification);
            });
        }
    }

    private void seedPromotions() {
        if (promotionRepository.count() == 0) {
            List<SportsField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 10).forEach(index -> {
                Promotion promotion = Promotion.builder()
                        .name(faker.commerce().promotionCode())
                        .description(faker.lorem().sentence(20))
                        .discountPercentage(faker.number().randomDouble(2, 5, 50))
                        .startDate(faker.date().past(30, TimeUnit.DAYS))
                        .endDate(faker.date().future(30, TimeUnit.DAYS))
                        .sportsField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .build();

                promotionRepository.save(promotion);
            });
        }
    }

    private void seedBookingItems() {
        if (bookingRepository.count() > 0) {
            List<BookingItems> bookingItemsList = bookingItemRepository.findAll();

            bookingItemsList.forEach(bookingItems -> {
                bookingItemRepository.save(
                        BookingItems.builder()
                                .booking(bookingItems.getBooking())
                                .availableDate(bookingItems.getAvailableDate())
                                .startTime(bookingItems.getStartTime())
                                .endTime(bookingItems.getEndTime())
                                .pricePerHour(bookingItems.getPricePerHour())
                                .build()
                );
            });
        }
    }


    private void seedStatistics() {
        if (statisticRepository.count() == 0) {
            IntStream.range(0, 10).forEach(index -> {
                Date date = Date.from(LocalDate.now().minusDays(faker.number().numberBetween(1, 30))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                Statistic statistics = Statistic.builder()
                        .date(date)
                        .totalOrders(faker.number().numberBetween(0, 100))
                        .totalPayments(faker.number().numberBetween(0, 100))
                        .totalRevenue(faker.number().numberBetween(1000, 10000))
                        .activeUsers(faker.number().numberBetween(0, 50))
                        .build();

                statisticRepository.save(statistics);
            });
        }
    }

}
