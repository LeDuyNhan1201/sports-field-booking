package org.jakartaee5g23.sportsfieldbooking.services.impls;

import org.jakartaee5g23.sportsfieldbooking.exceptions.dashboard.DashboardErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.dashboard.DashboardException;
import org.jakartaee5g23.sportsfieldbooking.repositories.UserRepository;
import org.jakartaee5g23.sportsfieldbooking.services.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;

@Service
public class DashboardServiceImpl implements DashboardService {

    UserRepository userRepository;

    @Override
    public String getSummary() {
        if(userRepository.findAll().size() == 0) {
            throw new DashboardException(DashboardErrorCode.USERS_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return getLocalizedMessage("hello_admin");
    }

}
