package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    Role findById(int id);

    Role findByName(String name);

}
