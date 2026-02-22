package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final BusinessManager bm;

    public RestaurantService(BusinessManager bm) {
        this.bm = bm;
    }

    public Restaurant create(Restaurant r) {
        return bm.createRestaurant(r);
    }

    public Restaurant save(Restaurant r) {
        return bm.saveRestaurant(r);
    }

    public List<Restaurant> findAll() {
        return bm.findAllRestaurants();
    }

    public Optional<Restaurant> findById(Integer id) {
        return Optional.ofNullable(bm.findRestaurantById(id));
    }

    @Transactional
    public Optional<Restaurant> update(Integer id, Restaurant updates) {
        Restaurant existing = bm.findRestaurantById(id);
        if (existing == null) return Optional.empty();

        if (updates.getRestaurantName() != null) existing.setRestaurantName(updates.getRestaurantName());
        if (updates.getLocation() != null) existing.setLocation(updates.getLocation());
        if (updates.getType() != null) existing.setType(updates.getType());
        if (updates.getCuisine() != null) existing.setCuisine(updates.getCuisine());

        Restaurant saved = bm.updateRestaurant(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteRestaurantById(id);
    }

    // Optional convenience method: find restaurants by location id
    public List<Restaurant> findByLocationId(Integer locationId) {
        return bm.findRestaurantsByLocationId(locationId);
    }
}