package com.example.restaurant.repository;

import com.example.restaurant.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByCityAndState(String city, String state);
}
