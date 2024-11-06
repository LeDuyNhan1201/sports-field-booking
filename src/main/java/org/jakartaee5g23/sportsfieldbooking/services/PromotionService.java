package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.springframework.data.domain.Page;

public interface PromotionService {
    Promotion create(Promotion request, Boolean isConfirmed);

    Page<Promotion> findAll(int offset, int limit);

    Promotion findById(Integer id);

    Promotion update(Promotion request, Boolean isConfirmed);

    Promotion updateStatus(Integer id, PromotionStatus status);
}