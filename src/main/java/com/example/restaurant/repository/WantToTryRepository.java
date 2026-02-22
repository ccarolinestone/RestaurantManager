package com.example.restaurant.repository;

import com.example.restaurant.model.WantToTry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WantToTryRepository extends JpaRepository<WantToTry, Integer> {
    List<WantToTry> findByRestaurantId(Integer restaurantId);
}
