package com.example.restaurant.repository;

import com.example.restaurant.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findByHaveTriedId(Integer haveTriedId);
}