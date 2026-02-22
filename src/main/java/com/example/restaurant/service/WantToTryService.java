package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.WantToTry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WantToTryService {

    private final BusinessManager bm;

    public WantToTryService(BusinessManager bm) {
        this.bm = bm;
    }

    public WantToTry create(WantToTry w) {
        return bm.createWantToTry(w);
    }

    public WantToTry save(WantToTry w) {
        return bm.saveWantToTry(w);
    }

    public List<WantToTry> findAll() {
        return bm.findAllWantToTry();
    }

    public Optional<WantToTry> findById(Integer id) {
        return Optional.ofNullable(bm.findWantToTryById(id));
    }

    @Transactional
    public Optional<WantToTry> update(Integer id, WantToTry updates) {
        WantToTry existing = bm.findWantToTryById(id);
        if (existing == null) return Optional.empty();

        if (updates.getRestaurant() != null) existing.setRestaurant(updates.getRestaurant());
        // add other fields if WantToTry grows

        WantToTry saved = bm.updateWantToTry(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteWantToTryById(id);
    }

    public List<WantToTry> findByRestaurantId(Integer restaurantId) {
        return bm.findWantToTryByRestaurantId(restaurantId);
    }
}

