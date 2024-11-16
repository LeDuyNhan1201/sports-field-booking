package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.FileMetadata;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<User> findAll(int offset, int limit);

    User findByEmail(String email);

    User findById(String id);

    boolean existsByEmail(String email);

    User create(User user);

    void updatePassword(User user, String password);

    void activateUser(User user);

    void update(User user);

    void deleteUser(String id);
}