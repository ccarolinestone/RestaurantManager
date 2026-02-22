package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.Rating;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final BusinessManager bm;

    public RatingService(BusinessManager bm) {
        this.bm = bm;
    }

    public Rating create(Rating r) {
        return bm.createRating(r);
    }

    public Rating save(Rating r) {
        return bm.saveRating(r);
    }

    public List<Rating> findAll() {
        return bm.findAllRatings();
    }

    public Optional<Rating> findById(Integer id) {
        return Optional.ofNullable(bm.findRatingById(id));
    }

    @Transactional
    public Optional<Rating> update(Integer id, Rating updates) {
        Rating existing = bm.findRatingById(id);
        if (existing == null) return Optional.empty();

        if (updates.getFoodRating() != null) existing.setFoodRating(updates.getFoodRating());
        if (updates.getVibeRating() != null) existing.setVibeRating(updates.getVibeRating());
        if (updates.getRatingDescription() != null) existing.setRatingDescription(updates.getRatingDescription());
        if (updates.getHaveTried() != null) existing.setHaveTried(updates.getHaveTried());

        Rating saved = bm.updateRating(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteRatingById(id);
    }

    public List<Rating> findByHaveTriedId(Integer haveTriedId) {
        return bm.findRatingsByHaveTriedId(haveTriedId);
    }
}