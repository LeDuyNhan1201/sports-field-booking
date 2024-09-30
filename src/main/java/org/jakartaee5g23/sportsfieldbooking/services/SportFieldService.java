package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.springframework.data.domain.Page;

public interface SportFieldService {

    SportField findById(String id);

    Page<SportField> findAll(int offset, int limit);

    SportField create(SportField request, Boolean isConfirmed);

    SportField update(SportField request, Boolean isConfirmed);

    SportField updateStatus(String id, SportFieldStatus status);

}
