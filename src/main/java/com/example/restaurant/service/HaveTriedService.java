package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.HaveTried;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HaveTriedService {

    private final BusinessManager bm;

    public HaveTriedService(BusinessManager bm) {
        this.bm = bm;
    }

    public HaveTried create(HaveTried h) {
        return bm.createHaveTried(h);
    }

    public HaveTried save(HaveTried h) {
        return bm.saveHaveTried(h);
    }

    public List<HaveTried> findAll() {
        return bm.findAllHaveTried();
    }

    public Optional<HaveTried> findById(Integer id) {
        return Optional.ofNullable(bm.findHaveTriedById(id));
    }

    @Transactional
    public Optional<HaveTried> update(Integer id, HaveTried updates) {
        HaveTried existing = bm.findHaveTriedById(id);
        if (existing == null) return Optional.empty();

        if (updates.getRestaurant() != null) existing.setRestaurant(updates.getRestaurant());
        // add other fields if needed

        HaveTried saved = bm.updateHaveTried(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteHaveTriedById(id);
    }

    public List<HaveTried> findByRestaurantId(Integer restaurantId) {
        return bm.findHaveTriedByRestaurantId(restaurantId);
    }
}