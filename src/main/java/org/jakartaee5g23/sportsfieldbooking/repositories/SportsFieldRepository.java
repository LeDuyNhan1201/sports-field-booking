package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsFieldRepository extends JpaRepository<SportsField, String> {

}