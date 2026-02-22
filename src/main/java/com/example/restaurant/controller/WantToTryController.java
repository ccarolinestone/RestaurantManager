package com.example.restaurant.controller;

import com.example.restaurant.model.WantToTry;
import com.example.restaurant.service.WantToTryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wanttotry")
public class WantToTryController {

    private final WantToTryService service;

    public WantToTryController(WantToTryService service) {
        this.service = service;
    }

    @GetMapping
    public List<WantToTry> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<WantToTry> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public List<WantToTry> byRestaurant(@PathVariable Integer restaurantId) {
        return service.findByRestaurantId(restaurantId);
    }

    @PostMapping
    public ResponseEntity<WantToTry> create(@RequestBody WantToTry w) {
        WantToTry saved = service.create(w);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WantToTry> update(@PathVariable Integer id, @RequestBody WantToTry w) {
        return service.update(id, w).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

