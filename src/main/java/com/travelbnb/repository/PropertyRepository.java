package com.travelbnb.repository;

import com.travelbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p JOIN p.location l JOIN p.country c WHERE l.name = :locationName OR c.name = :locationName")
    List<Property> searchProperty(@Param("locationName") String locationName);




}