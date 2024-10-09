package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.Date;

import org.jakartaee5g23.sportsfieldbooking.entities.SportField;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.springframework.data.domain.Page;

public interface SportFieldService {

    SportField findById(String id);

    Page<SportField> findAll(int offset, int limit);

    SportField create(SportField request, Boolean isConfirmed);

    SportField update(SportField request, Boolean isConfirmed);

    SportField updateStatus(String id, SportFieldStatus status);

    Page<SportField> searchSportFields(String name, String location, Date time, Double minPrice, Double maxPrice,
            Integer categoryId, int offset, int limit);

}
