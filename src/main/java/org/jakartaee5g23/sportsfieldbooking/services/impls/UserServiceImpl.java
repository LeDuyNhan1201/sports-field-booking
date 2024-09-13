package org.jakartaee5g23.sportsfieldbooking.services.impls;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.UserInfoResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.Gender;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.mappers.UserMapper;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.jakartaee5g23.sportsfieldbooking.enums.Gender.MALE;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper = UserMapper.INSTANCE;

    PasswordEncoder passwordEncoder;

    // Generate fake data
    @PostConstruct
    public void generateAndSaveFakeUsers() {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();

        User myUser = User.builder()
                .email("benlun99999@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .mobileNumber(faker.phoneNumber().cellPhone())
                .username("benlun99999")
                .firstName("Ben")
                .middleName("Nguyen")
                .lastName("Lun")
                .birthdate(LocalDate.now().minusYears(21))
                .gender(MALE)
                .isActivated(true)
                .avatar(null)
                .build();
        users.add(myUser);

        for (int i = 0; i < 20; i++) { // Generate 10 fake users
            String email = faker.internet().emailAddress();
            String password = passwordEncoder.encode(email);
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setMobileNumber(faker.phoneNumber().cellPhone());
            user.setUsername(faker.name().username());
            user.setFirstName(faker.name().firstName());
            user.setMiddleName(faker.name().lastName());
            user.setLastName(faker.name().lastName());
            user.setBirthdate(LocalDate.now().minusYears(faker.number().numberBetween(18, 60)));
            user.setGender(Gender.values()[faker.number().numberBetween(0, 2)]);
            user.setActivated(true);
            user.setBio(faker.lorem().sentence());
            user.setAvatar(null);
            users.add(user);

        }
        userRepository.saveAll(users);
    }

    @Override
    @PostAuthorize("returnObject.email == authentication.name") // CHECK OWNER
    public UserInfoResponse getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = findByEmail(email);
        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AuthenticationException(USER_NOT_FOUND, NOT_FOUND));
        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new AuthenticationException(USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new AuthenticationException(USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateUser(User user) {
        user.setActivated(true);
        userRepository.save(user);
    }

}