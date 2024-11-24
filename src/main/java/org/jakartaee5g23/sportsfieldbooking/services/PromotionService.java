package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.springframework.data.domain.Page;

public interface PromotionService {
    Promotion create(Promotion request, Boolean isConfirmed);

    Page<Promotion> findAll(int offset, int limit);

    Promotion findById(String id);

    Promotion update(String id, Promotion request, Boolean isConfirmed);

    Promotion updateStatus(String id, PromotionStatus status);

    void delete(String id);
}