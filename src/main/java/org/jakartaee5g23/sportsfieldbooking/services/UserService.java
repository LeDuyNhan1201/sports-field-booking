package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.dtos.responses.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;

public interface UserService {

    UserResponse getMyInfo();

    UserResponse getUserInfo(String userId);

    User findByEmail(String email);

    User findById(String id);

    boolean existsByEmail(String email);

    User createUser(User user);

    void updatePassword(User user, String password);

    void activateUser(User user);

}