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

import java.time.LocalDate;
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
    FileMetadataRepository fileMetadataRepository;
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
//        seedFieldImages();
        seedReviews();
        seedFieldAvailabilities();
        seedBookings();
        seedBookingItems();
        seedPayments();
        seedNotifications();
        seedPromotions();
        seedStatistics();
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
            IntStream.range(0, 20).forEach(_ -> {
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

    private String seedCreatedBy() {
        return userRepository.findAll().get(faker.number().numberBetween(0, userRepository.findAll().size())).getId();
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            IntStream.range(0, 5).forEach(_ -> {
                Category category = Category.builder().name(faker.team().sport()).createdBy(seedCreatedBy()).build();
                categoryRepository.save(category);
            });
        }
    }

    private void seedSportFields() {
        if (sportFieldRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Category> categories = categoryRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                SportsField field = SportsField.builder()
                        .name(faker.team().name())
                        .location(faker.address().fullAddress())
                        .opacity(faker.number().numberBetween(1, 100))
                        .openingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .closingTime(faker.date().future(365 * 5, TimeUnit.DAYS)) // Updated to future
                        .category(categories.get(faker.number().numberBetween(0, categories.size())))
                        .user(createdBy)
                        .rating(faker.number().randomDouble(2, 0, 5))
                        .status(getRandomEnum(SportsFieldStatus.class))
                        .createdBy(createdBy.getId())
                        .build();

                sportFieldRepository.save(field);
            });
        }
    }

    private void seedFieldImages() {
        if (sportFieldRepository.count() * 2 == fileMetadataRepository.count()) {
            List<SportsField> fields = sportFieldRepository.findAll();
            List<FileMetadata> images = fileMetadataRepository.findAll();

            int imageIndex = 0;
            for (SportsField field : fields) {

                for (int i = 0; i < 2; i++) { // Thêm 2 hình ảnh cho mỗi sports field
                    FileMetadata image = images.get(imageIndex);
                    image.setCreatedBy(field.getUser().getId());
                    image.setSportsField(field);
                    fileMetadataRepository.save(image); // Lưu lại hình ảnh với createdBy là người sở hữu sports field
                    imageIndex++;
                }
            }

        }
    }

    private void seedFieldAvailabilities() {
        if (fieldAvailabilityRepository.count() == 0) {
            List<SportsField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 30).forEach(_ -> {
                SportsField field = fields.get(faker.number().numberBetween(0, fields.size()));
                // Create a date that is available in the next 30 days
                Date availableDate = faker.date().future(30, TimeUnit.DAYS);
                //
                // // Generate start time between 8:00 AM and 8:00 PM
                LocalDate localDate = availableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date startTime = Date.from(localDate.atTime(faker.number().numberBetween(8, 20), 0)
                        .atZone(ZoneId.systemDefault()).toInstant());

                // Generate end time between 1 to 12 hours after start time
                Date endTime = new Date(
                        startTime.getTime() + (long) faker.number().numberBetween(1, 12) * 60 * 60 * 1000);

                FieldAvailability availability = FieldAvailability.builder()
                        .sportsField(field)
                        .availableDate(availableDate)
                        .startTime(startTime)
                        .price(faker.number().randomDouble(2, 10, 100))
                        .endTime(endTime)
                        .isAvailable(faker.bool().bool())
                        .createdBy(field.getUser().getId())
                        .build();

                fieldAvailabilityRepository.save(availability);
            });
        }
    }

    private void seedBookings() {
        if (bookingRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<FieldAvailability> availabilities = fieldAvailabilityRepository.findAll();

            IntStream.range(0, 20).forEach(i -> {
                FieldAvailability availability = availabilities.get(i);
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));

                Booking booking = Booking.builder()
                        .user(createdBy)
                        .fieldAvailability(availability)
                        .status(getRandomEnum(BookingStatus.class))
                        .createdBy(createdBy.getId())
                        .build();

                bookingRepository.save(booking);
            });
        }
    }

    private void seedBookingItems() {
        if (bookingItemRepository.count() == 0) {
            List<Booking> bookings = bookingRepository.findAll();

            bookings.forEach(booking -> {

                int itemCount = faker.number().numberBetween(1, 5);

                for (int i = 0; i < itemCount; i++) {
                    Date availableDate = faker.date().past(30, TimeUnit.DAYS);
                    LocalDate localDate = availableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Date startTime = Date.from(localDate.atTime(faker.number().numberBetween(8, 20), 0)
                            .atZone(ZoneId.systemDefault()).toInstant());

                    Date endTime = new Date(
                            startTime.getTime() + (long) faker.number().numberBetween(1, 3) * 60 * 60 * 1000);

                    BookingItem bookingItem = BookingItem.builder()
                            .booking(booking)
                            .availableDate(availableDate)
                            .startTime(startTime)
                            .endTime(endTime)
                            .price(booking.getFieldAvailability().getPrice())
                            .createdBy(booking.getUser().getId())
                            .build();

                    bookingItemRepository.save(bookingItem);
                }
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

                Payment payment = Payment.builder()
                        .method(getRandomEnum(PaymentMethod.class))
                        .price(fieldAvailability.getPrice())
                        .booking(booking)
                        .status(getRandomEnum(PaymentStatus.class))
                        .createdBy(booking.getUser().getId())
                        .build();

                paymentRepository.save(payment);
            });
        }
    }

    private void seedReviews() {
        if (reviewRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<SportsField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Review review = Review.builder()
                        .user(createdBy)
                        .sportsField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .comment(faker.lorem().sentence(15))
                        .createdBy(createdBy.getId())
                        .build();

                reviewRepository.save(review);
            });

            IntStream.range(0, 3).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Review review = Review.builder()
                        .user(createdBy)
                        .sportsField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .comment(faker.lorem().sentence(15))
                        .parentReview(reviewRepository.findAll()
                                .get(faker.number().numberBetween(0, reviewRepository.findAll().size())))
                        .createdBy(createdBy.getId())
                        .build();

                reviewRepository.save(review);
            });
        }
    }

    private void seedNotifications() {
        if (notificationRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Booking> bookings = bookingRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Notification notification = Notification.builder()
                        .user(createdBy)
                        .booking(bookings.get(faker.number().numberBetween(0, bookings.size())))
                        .type(getRandomEnum(NotificationType.class))
                        .message(faker.lorem().sentence(10))
                        .createdBy(createdBy.getId())
                        .build();

                notificationRepository.save(notification);
            });
        }
    }

    private void seedPromotions() {
        if (promotionRepository.count() == 0) {
            IntStream.range(0, 10).forEach(_ -> {
                Promotion promotion = Promotion.builder()
                        .name(faker.commerce().promotionCode())
                        .description(faker.lorem().sentence(20))
                        .discountPercentage(faker.number().randomDouble(2, 5, 50))
                        .status(getRandomEnum(PromotionStatus.class))
                        .startDate(faker.date().past(30, TimeUnit.DAYS))
                        .endDate(faker.date().future(30, TimeUnit.DAYS))
                        .createdBy(seedCreatedBy())
                        .build();

                promotionRepository.save(promotion);
            });
        }
    }

    private void seedStatistics() {
        if (statisticRepository.count() == 0) {
            IntStream.range(0, 10).forEach(_ -> {
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
