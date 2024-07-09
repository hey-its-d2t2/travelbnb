package com.travelbnb.repository;

import com.travelbnb.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
}