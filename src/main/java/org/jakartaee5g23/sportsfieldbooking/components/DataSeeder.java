package org.jakartaee5g23.sportsfieldbooking.components;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.*;
import org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileException;
import org.jakartaee5g23.sportsfieldbooking.helpers.Constants;
import org.jakartaee5g23.sportsfieldbooking.repositories.*;
import org.jakartaee5g23.sportsfieldbooking.services.MinioClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.file.FileErrorCode.FILE_NOT_FOUND;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Utils.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    MinioClientService minioClientService;

    @Value("${minio.bucket-name}")
    @NonFinal
    String bucketName;

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
        seedBookingItems();
        seedPayments();
        seedNotifications();
        seedPromotions();
        seedStatistics();
        seedFiles();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role user = Role.builder().name("USER").build();
            Role fieldOwner = Role.builder().name("FIELD_OWNER").build();
            Role admin = Role.builder().name("ADMIN").build();
            roleRepository.saveAll(List.of(user, fieldOwner, admin));
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            List<User> users = new ArrayList<>();
            IntStream.range(0, 200).forEach(_ -> {
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
                users.add(user);
            });
            userRepository.saveAll(users);
        }
    }

    private void seedUserRole() {
        if (userRoleRepository.count() == 0) {
            List<Role> roles = roleRepository.findAll();
            List<User> users = userRepository.findAll();
            List<UserRole> userRoles = new ArrayList<>();
            users.forEach(user -> {
                UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(roles.getFirst())
                        .build();

                userRoles.add(userRole);
            });

            users.forEach(user -> {
                UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(roles.get(faker.number().numberBetween(1, roles.size())))
                        .build();

                userRoles.add(userRole);
            });

            userRoleRepository.saveAll(userRoles);
        }
    }

    private String seedCreatedBy() {
        return userRepository.findAll().get(faker.number().numberBetween(0, userRepository.findAll().size())).getId();
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = new ArrayList<>();
            IntStream.range(0, 5).forEach(_ -> {
                Category category = Category.builder().name(faker.team().sport()).createdBy(seedCreatedBy()).build();
                categories.add(category);
            });
            categoryRepository.saveAll(categories);
        }
    }

    private void seedSportFields() {
        if (sportFieldRepository.count() == 0) {
            List<User> users = userRepository.findAllByRoles(List.of(roleRepository.findByName("FIELD_OWNER").get()));
            List<Category> categories = categoryRepository.findAll();
            List<SportsField> fields = new ArrayList<>();

            for (User user : users) {
                SportsField field = SportsField.builder()
                        .name(faker.team().name())
                        .location(faker.address().fullAddress())
                        .opacity(faker.number().numberBetween(1, 100))
                        .openingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .closingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .category(categories.get(faker.number().numberBetween(0, categories.size())))
                        .user(user)
                        .rating(faker.number().randomDouble(2, 0, 5))
                        .status(getRandomEnum(SportsFieldStatus.class))
                        .createdBy(user.getId())
                        .build();

                fields.add(field);
            }
            sportFieldRepository.saveAll(fields);
        }
    }

    private void seedFieldAvailabilities() {
        if (fieldAvailabilityRepository.count() == 0) {
            List<SportsField> fields = sportFieldRepository.findAll();
            List<FieldAvailability> availabilities = new ArrayList<>();
            for (SportsField field : fields) {
                int availabilityCount = faker.number().numberBetween(1, 5);
                for (int i = 0; i < availabilityCount; i++) {
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
                            .startTime(startTime)
                            .price(faker.number().randomDouble(2, 10, 100))
                            .endTime(endTime)
                            .createdBy(field.getUser().getId())
                            .build();

                    availabilities.add(availability);
                }
            }
            fieldAvailabilityRepository.saveAll(availabilities);
        }
    }

    private void seedBookings() {
        if (bookingRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Booking> bookings = new ArrayList<>();
            IntStream.range(0, users.size()).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Booking booking = Booking.builder()
                        .user(createdBy)
                        .status(getRandomEnum(BookingStatus.class))
                        .createdBy(createdBy.getId())
                        .build();

                bookings.add(booking);
            });
            bookingRepository.saveAll(bookings);
        }
    }

    private void seedBookingItems() {
        if (bookingItemRepository.count() == 0) {
            List<Booking> bookings = bookingRepository.findAll();
            List<SportsField> sportsFields = sportFieldRepository.findAll();
            List<BookingItem> bookingItems = new ArrayList<>();
            bookings.forEach(booking -> {
                int itemCount = faker.number().numberBetween(1, 3);
                for (int i = 0; i < itemCount; i++) {
                    Date availableDate = faker.date().past(30, TimeUnit.DAYS);
                    LocalDate localDate = availableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Date startTime = Date.from(localDate.atTime(faker.number().numberBetween(8, 20), 0)
                            .atZone(ZoneId.systemDefault()).toInstant());

                    Date endTime = new Date(
                            startTime.getTime() + (long) faker.number().numberBetween(1, 3) * 60 * 60 * 1000);

                    SportsField randomSportsField = sportsFields.get(
                            faker.number().numberBetween(0, sportsFields.size()));

                    BookingItem bookingItem = BookingItem.builder()
                            .booking(booking)
                            .availableDate(availableDate)
                            .startTime(startTime)
                            .endTime(endTime)
                            .price(faker.number().randomDouble(2, 10, 50))
                            .createdBy(booking.getUser().getId())
                            .sportsField(randomSportsField)
                            .status(BookingItemStatus.PENDING)
                            .build();

                    bookingItems.add(bookingItem);
                }
            });
            bookingItemRepository.saveAll(bookingItems);
        }
    }

    private void seedPayments() {
        if (paymentRepository.count() == 0) {
            List<Booking> bookings = bookingRepository.findAll();
            Map<Booking, List<BookingItem>> bookingItemsMap = bookingItemRepository.findAll()
                    .stream()
                    .collect(Collectors.groupingBy(BookingItem::getBooking));

            Set<Booking> usedBookings = new HashSet<>();
            List<Payment> payments = new ArrayList<>();

            bookings.forEach(booking -> {
                if (usedBookings.contains(booking)) return;
                usedBookings.add(booking);

                List<BookingItem> bookingItems = bookingItemsMap.getOrDefault(booking, Collections.emptyList());
                Double totalPrice = bookingItems.stream().mapToDouble(BookingItem::getPrice).sum();

                Payment payment = Payment.builder()
                        .method(getRandomEnum(PaymentMethod.class))
                        .price(totalPrice)
                        .booking(booking)
                        .status(getRandomEnum(PaymentStatus.class))
                        .createdBy(booking.getUser().getId())
                        .build();

                payments.add(payment);
            });

            paymentRepository.saveAll(payments);
        }
    }


    private void seedReviews() {
        if (reviewRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<SportsField> fields = sportFieldRepository.findAll();
            List<Review> reviews = new ArrayList<>();
            IntStream.range(0, 200).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Review review = Review.builder()
                        .user(createdBy)
                        .sportsField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .comment(faker.lorem().sentence(15))
                        .createdBy(createdBy.getId())
                        .build();

                reviews.add(review);
            });
            reviewRepository.saveAll(reviews);
            reviews.clear();

            List<Review> parentReviews = reviewRepository.findAll();
            IntStream.range(0, parentReviews.size() * 2).forEach(_ -> {
                User createdBy = users.get(faker.number().numberBetween(0, users.size()));
                Review parentReview = parentReviews.get(faker.number().numberBetween(0, parentReviews.size()));
                Review review = Review.builder()
                        .user(createdBy)
                        .sportsField(parentReview.getSportsField())
                        .comment(faker.lorem().sentence(15))
                        .parentReview(parentReview)
                        .createdBy(createdBy.getId())
                        .build();

                reviews.add(review);
            });

            reviewRepository.saveAll(reviews);
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

    private void seedFiles() {
        if (fileMetadataRepository.count() == 0) {
            List<SportsField> fields = sportFieldRepository.findAll();
            for (SportsField field : fields) {
                int imageCount = faker.number().numberBetween(1, 5);
                for (int i = 0; i < imageCount; i++) { // Thêm 2 hình ảnh cho mỗi sports field
                    File randomFile = getRandomFile(Constants.SPORTS_FIELD_FAKE_IMAGES_FOLDER);
                    String contentType = getContentType(randomFile);
                    long size = randomFile.length();
                    String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
                    FileMetadata fileMetadata = FileMetadata.builder()
                            .objectKey(fileName)
                            .size(size)
                            .contentType(contentType)
                            .sportsField(field)
                            .createdBy(field.getUser().getId())
                            .createdAt(field.getCreatedAt())
                            .build();
                    fileMetadataRepository.save(fileMetadata);
                    minioClientService.storeObject(randomFile, fileName, contentType, bucketName);
                }
            }

            List<User> users = userRepository.findAll();
            for (User user : users) {
                File randomFile = getRandomFile(Constants.USER_FAKE_AVATARS_FOLDER);
                String contentType = getContentType(randomFile);
                long size = randomFile.length();
                String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
                FileMetadata fileMetadata = FileMetadata.builder()
                        .objectKey(fileName)
                        .size(size)
                        .contentType(contentType)
                        .user(user)
                        .createdBy(user.getId())
                        .createdAt(user.getCreatedAt())
                        .build();
                fileMetadataRepository.save(fileMetadata);
                minioClientService.storeObject(randomFile, fileName, contentType, bucketName);
            }
        }
    }

    private String getContentType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new FileException(FILE_NOT_FOUND, NOT_FOUND);
        }
    }

}
