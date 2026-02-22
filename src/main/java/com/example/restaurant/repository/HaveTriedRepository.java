package com.example.restaurant.repository;

import com.example.restaurant.model.HaveTried;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaveTriedRepository extends JpaRepository<HaveTried, Integer> {
    List<HaveTried> findByRestaurantId(Integer restaurantId);
}
