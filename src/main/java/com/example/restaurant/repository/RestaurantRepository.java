package com.example.restaurant.repository;

import com.example.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByLocationId(Integer locationId);
    List<Restaurant> findByRestaurantNameContainingIgnoreCase(String name);
}
