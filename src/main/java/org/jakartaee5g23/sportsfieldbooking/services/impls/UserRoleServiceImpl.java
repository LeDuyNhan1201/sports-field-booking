package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.entities.UserRole;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRoleRepository;
import org.jakartaee5g23.sportsfieldbooking.services.UserRoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    UserRoleRepository userRoleRepository;
    @Override
    public UserRole create(UserRole role) {
        return userRoleRepository.save(role);
    }
}
