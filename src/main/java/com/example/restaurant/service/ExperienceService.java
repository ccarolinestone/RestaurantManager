package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.Experience;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {

    private final BusinessManager bm;

    public ExperienceService(BusinessManager bm) {
        this.bm = bm;
    }

    public Experience create(Experience e) {
        return bm.createExperience(e);
    }

    public Experience save(Experience e) {
        return bm.saveExperience(e);
    }

    public List<Experience> findAll() {
        return bm.findAllExperiences();
    }

    public Optional<Experience> findById(Integer id) {
        return Optional.ofNullable(bm.findExperienceById(id));
    }

    @Transactional
    public Optional<Experience> update(Integer id, Experience updates) {
        Experience existing = bm.findExperienceById(id);
        if (existing == null) return Optional.empty();

        if (updates.getDate() != null) existing.setDate(updates.getDate());
        if (updates.getDescription() != null) existing.setDescription(updates.getDescription());
        if (updates.getHaveTried() != null) existing.setHaveTried(updates.getHaveTried());

        Experience saved = bm.updateExperience(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteExperienceById(id);
    }

    public List<Experience> findByHaveTriedId(Integer haveTriedId) {
        return bm.findExperiencesByHaveTriedId(haveTriedId);
    }
}