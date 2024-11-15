package org.jakartaee5g23.sportsfieldbooking.services;

import java.util.List;

import org.jakartaee5g23.sportsfieldbooking.entities.Category;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.springframework.data.domain.Page;

public interface CategoryService {
    List<Category> findAll();
    
} 
