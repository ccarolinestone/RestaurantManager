package com.example.restaurant.service;

import com.example.restaurant.business.BusinessManager;
import com.example.restaurant.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final BusinessManager bm;

    public LocationService(BusinessManager bm) {
        this.bm = bm;
    }

    public Location create(Location loc) {
        return bm.createLocation(loc);
    }

    public Location save(Location loc) {
        return bm.saveLocation(loc);
    }

    public List<Location> findAll() {
        return bm.findAllLocations();
    }

    public Optional<Location> findById(Integer id) {
        return Optional.ofNullable(bm.findLocationById(id));
    }

    @Transactional
    public Optional<Location> update(Integer id, Location updates) {
        Location existing = bm.findLocationById(id);
        if (existing == null) return Optional.empty();

        if (updates.getCity() != null) existing.setCity(updates.getCity());
        if (updates.getState() != null) existing.setState(updates.getState());

        Location saved = bm.updateLocation(existing);
        return Optional.ofNullable(saved);
    }

    public boolean delete(Integer id) {
        return bm.deleteLocationById(id);
    }
}
