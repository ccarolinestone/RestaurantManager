package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.Dish;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * DishService - thin service layer that delegates to BusinessManager.
 * This service performs the merge/validation for updates, then calls
 * BusinessManager.updateDish(existing) to persist.
 */
@Service
public class DishService {

    private final BusinessManager bm;

    public DishService(BusinessManager bm) {
        this.bm = bm;
    }

    // Create new dish
    public Dish create(Dish d) {
        return bm.createDish(d);
    }

    // Save (create or update)
    public Dish save(Dish d) {
        return bm.saveDish(d);
    }

    // Get all dishes
    public List<Dish> findAll() {
        return bm.findAllDishes();
    }

    // Get by id
    public Optional<Dish> findById(Integer id) {
        return Optional.ofNullable(bm.findDishById(id));
    }

    // Find by haveTried id (you must implement this in BusinessManager or repository)
    public List<Dish> findByHaveTriedId(Integer haveTriedId) {
        return bm.findDishesByHaveTriedId(haveTriedId);
    }

    /**
     * Update merge method (read existing, apply non-null updates, then persist).
     * This matches BusinessManager.updateDish(Dish existing).
     */
    @Transactional
    public Optional<Dish> update(Integer id, Dish updates) {
        Dish existing = bm.findDishById(id);
        if (existing == null) return Optional.empty();

        if (updates.getDishName() != null) existing.setDishName(updates.getDishName());
        if (updates.getFoodDescription() != null) existing.setFoodDescription(updates.getFoodDescription());
        if (updates.getHaveTried() != null) existing.setHaveTried(updates.getHaveTried());

        Dish saved = bm.updateDish(existing);
        return Optional.ofNullable(saved);
    }

    // Delete by id
    public boolean delete(Integer id) {
        return bm.deleteDishById(id);
    }
}