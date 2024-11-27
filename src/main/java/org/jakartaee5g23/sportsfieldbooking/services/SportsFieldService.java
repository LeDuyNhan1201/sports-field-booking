package org.jakartaee5g23.sportsfieldbooking.services;

import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.specifications.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface SportsFieldService {

        SportsField findById(String id);

        Page<SportsField> findAll(int offset, int limit, String colSort, int sortDirection);

        SportsField create(SportsField request, Boolean isConfirmed);

        SportsField update(SportsField request, Boolean isConfirmed);

        SportsField updateStatus(String id, SportsFieldStatus status);
        SportsField updatePromotion(String id, String promotion);
        Page<SportsField> searchSportsField(String userId,String text,
                                            Double maxPrice, Double minPrice, Integer categoryId, Integer onlyActiveStatus, int offset, int limit , String colSort, int sortDirection);

        Page<SportsField> findByUser(User user, int offset, int limit, String colSort, int sortDirection);

        Page<SportsField> findSportsFieldsByCategoryLocationPrice(String categoryId, String location, double minPrice,
                        double maxPrice, int offset, int limit, String colSort, int sortDirection);

        Page<SportsField> searchSportsFields(
                List<Filter> filters, Integer categoryId, String userId, Double maxPrice, Double minPrice,
                List<Sort.Order> orders, int offset, int limit);
}
