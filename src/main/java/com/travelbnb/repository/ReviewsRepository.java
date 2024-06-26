package com.travelbnb.repository;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import com.travelbnb.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    @Query("select r from Reviews r where r.appUser=:user and r.property=:property")
    Reviews findReviewsByUser(@Param("user") AppUser user, @Param("property") Property property);
}