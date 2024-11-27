package org.jakartaee5g23.sportsfieldbooking.specifications;

import jakarta.persistence.criteria.*;
import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record SportsFieldSpecification(

        List<Filter> filters,

        Integer categoryId,

        String userId,

        Double maxPrice,

        Double minPrice,

        List<Sort.Order> orders

) implements Specification<SportsField> {

    @Override
    public Predicate toPredicate(@NotNull Root<SportsField> root, CriteriaQuery<?> query, @NotNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Xử lý các điều kiện từ Filter
        filters.forEach(filter -> {
            Predicate predicate = buildPredicate(filter, root, cb);
            if (filter.getLogic() == Filter.Logic.NOT) {
                predicate = cb.not(predicate);
            }
            if (filter.getLogic() == Filter.Logic.OR) {
                predicates.add(cb.or(predicate));
            } else {
                predicates.add(cb.and(predicate));
            }
        });

        // Join với bảng 'category'
        if (categoryId != null) {
            Join<SportsField, Category> categoryJoin = root.join("category", JoinType.LEFT);
            predicates.add(cb.equal(categoryJoin.get("id"), categoryId));
        }

        // Join với bảng 'user'
        if (userId != null) {
            Join<SportsField, User> userJoin = root.join("user", JoinType.LEFT);
            predicates.add(cb.equal(userJoin.get("id"), userId));
        }

        // Join với bảng 'fieldAvailabilities' và lọc giá
        if (maxPrice != null && minPrice != null) {
            Join<SportsField, FieldAvailability> availabilityJoin = root.join("fieldAvailabilities", JoinType.LEFT);
            predicates.add(cb.between(availabilityJoin.get("price"), minPrice, maxPrice));
        }

        // Áp dụng các điều kiện AND
        Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));

        // Sắp xếp
        if (!orders.isEmpty()) {
            query.orderBy(orders.stream()
                    .map(order -> order.isAscending()
                            ? cb.asc(root.get(order.getProperty()))
                            : cb.desc(root.get(order.getProperty())))
                    .toList());
        }

        return finalPredicate;
    }

    private Predicate buildPredicate(Filter filter, Root<SportsField> root, CriteriaBuilder cb) {
        String field = filter.getField();
        Object value = filter.getValue();

        return switch (filter.getOperator()) {
            case EQUAL -> cb.equal(root.get(field), value);
            case LIKE -> cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%");
            case GREATER_THAN -> cb.greaterThan(root.get(field), (Comparable) value);
            case LESS_THAN -> cb.lessThan(root.get(field), (Comparable) value);
            case GREATER_THAN_OR_EQUAL -> cb.greaterThanOrEqualTo(root.get(field), (Comparable) value);
            case LESS_THAN_OR_EQUAL -> cb.lessThanOrEqualTo(root.get(field), (Comparable) value);
        };
    }

}
