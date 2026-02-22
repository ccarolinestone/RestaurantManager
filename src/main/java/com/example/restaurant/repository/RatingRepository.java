package com.example.restaurant.repository;

import com.example.restaurant.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByHaveTriedId(Integer haveTriedId);
}
