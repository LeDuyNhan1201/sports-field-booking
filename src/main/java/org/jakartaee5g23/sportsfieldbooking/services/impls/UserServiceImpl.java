package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.UserStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(int offset, int limit) {
        return userRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        User existingUser = findById(user.getId());
        String hashedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(hashedPassword);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void activateUser(User user) {
        User existingUser = findById(user.getId());
        existingUser.setActivated(true);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void update(User user) {
        User existingUser = findById(user.getId());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobileNumber(user.getMobileNumber());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setGender(user.getGender());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setUsername(user.getUsername());
        existingUser.setStatus(user.getStatus());

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public Page<User> searchUsers(String keyword, UserStatus status, int offset, int limit) {
        return userRepository.searchUsers(keyword, status,
                PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }
}