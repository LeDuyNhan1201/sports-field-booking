package org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField;

import org.jakartaee5g23.sportsfieldbooking.entities.Order;
import org.springframework.data.domain.Page;
import java.util.*;

public record ListSportFieldResponse(
    List<Order> orders
) 
{}  
