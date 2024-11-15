package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.UserRole;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleService {
    UserRole create(UserRole role);
}
