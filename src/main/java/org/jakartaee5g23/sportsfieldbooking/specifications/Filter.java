package org.jakartaee5g23.sportsfieldbooking.specifications;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Filter {

    String field;        // Field name in the entity

    Object value;        // Value to filter

    Operator operator;   // Filter operator (EQUAL, LIKE, GREATER_THAN, etc.)

    Logic logic;         // Logic (AND, OR, NOT)

    public enum Operator {
        EQUAL, LIKE, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL
    }

    public enum Logic {
        AND, OR, NOT
    }

    // Constructor, getters, and setters
}
