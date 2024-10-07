package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.springframework.data.domain.Page;

public interface SportsFieldService {

    SportsField findById(String id);

    Page<SportsField> findAll(int offset, int limit);

    SportsField create(SportsField request, Boolean isConfirmed);

    SportsField update(SportsField request, Boolean isConfirmed);

    SportsField updateStatus(String id, SportsFieldStatus status);

}
