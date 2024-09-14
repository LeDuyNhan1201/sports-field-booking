package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.UserInfoResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.mappers.UserMapper;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper = UserMapper.INSTANCE;

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