package com.example.restaurant.repository;

import com.example.restaurant.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findByHaveTriedId(Integer haveTriedId);
}
