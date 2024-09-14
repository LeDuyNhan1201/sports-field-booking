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

    SportFieldRepository sportFieldRepository;

    CategoryRepository categoryRepository;

    PaymentRepository paymentRepository;

    OrderRepository orderRepository;

    ReviewRepository reviewRepository;

    PasswordEncoder passwordEncoder;

    Faker faker = new Faker();

    @PostConstruct
    @Transactional
    public void seed() {
        seedRoles();
        seedCategories();
        seedUsers();
        seedUserRole();
        seedSportFields();
        seedOrders();
        seedPayments();
        seedReviews();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {

            Role admin = Role.builder().name("ADMIN").build();

            Role customer = Role.builder().name("CUSTOMER").build();

            Role fieldOwner = Role.builder().name("FIELD_OWNER").build();

            roleRepository.saveAll(List.of(customer, fieldOwner, admin));
        }
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            IntStream.range(0, 5).forEach(_ -> {
                Category category = Category.builder().name(faker.team().sport()).build();
                categoryRepository.save(category);
            });
        }
    }

    private void seedUsers() {
        if (userRoleRepository.count() == 0) {
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
        Faker faker = new Faker();
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

    private void seedSportFields() {
        if (sportFieldRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Category> categories = categoryRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                SportField field = SportField.builder()
                        .name(faker.team().name())
                        .location(faker.address().fullAddress())
                        .pricePerHour(faker.number().numberBetween(50, 500))
                        .opacity(faker.number().numberBetween(1, 100))
                        .openingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .closingTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .category(categories.get(faker.number().numberBetween(0, categories.size())))
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .status(getRandomEnum(SportFieldStatus.class))
                        .build();

                sportFieldRepository.save(field);
            });
        }
    }

    private void seedOrders() {
        if (orderRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<SportField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                Order order = Order.builder()
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .sportField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .startTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .bookingHours(faker.number().randomDouble(2, 1, 5))
                        .endTime(faker.date().past(365 * 5, TimeUnit.DAYS))
                        .status(getRandomEnum(OrderStatus.class))
                        .build();

                orderRepository.save(order);
            });
        }
    }

    private void seedPayments() {
        if (paymentRepository.count() == 0) {
            List<Order> orders = orderRepository.findAll();

            IntStream.range(0, 20).forEach(i -> {
                Order order = orders.get(i);
                Payment payment = Payment.builder()
                        .method(getRandomEnum(PaymentMethod.class))
                        .price(order.getSportField().getPricePerHour() * order.getBookingHours())
                        .order(order)
                        .status(getRandomEnum(PaymentStatus.class))
                        .build();

                paymentRepository.save(payment);
            });
        }
    }

    private void seedReviews() {
        if (reviewRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<SportField> fields = sportFieldRepository.findAll();

            IntStream.range(0, 20).forEach(_ -> {
                Review review = Review.builder()
                        .user(users.get(faker.number().numberBetween(0, users.size())))
                        .sportField(fields.get(faker.number().numberBetween(0, fields.size())))
                        .comment(faker.lorem().sentence(15))
                        .rating(faker.number().randomDouble(2, 0, 5))
                        .build();

                reviewRepository.save(review);
            });
        }
    }
}
