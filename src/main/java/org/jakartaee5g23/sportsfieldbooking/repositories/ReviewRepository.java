package org.jakartaee5g23.sportsfieldbooking.repositories;

import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    Page<Review> findBySportsField(SportsField sportsField, Pageable pageable);

    Page<Review> findByParentReview(Review parentReview, Pageable pageable);

}