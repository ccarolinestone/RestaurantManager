package com.example.restaurant.controller;

import com.example.restaurant.model.HaveTried;
import com.example.restaurant.service.HaveTriedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/havetried")
public class HaveTriedController {

    private final HaveTriedService service;

    public HaveTriedController(HaveTriedService service) {
        this.service = service;
    }

    @GetMapping
    public List<HaveTried> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<HaveTried> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public List<HaveTried> byRestaurant(@PathVariable Integer restaurantId) {
        return service.findByRestaurantId(restaurantId);
    }

    @PostMapping
    public ResponseEntity<HaveTried> create(@RequestBody HaveTried h) {
        HaveTried saved = service.create(h);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HaveTried> update(@PathVariable Integer id, @RequestBody HaveTried h) {
        return service.update(id, h).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
