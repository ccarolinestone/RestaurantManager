package com.example.restaurant.controller;

import com.example.restaurant.model.Rating;
import com.example.restaurant.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rating> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-havetried/{haveTriedId}")
    public List<Rating> byHaveTried(@PathVariable Integer haveTriedId) {
        return service.findByHaveTriedId(haveTriedId);
    }

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating r) {
        Rating saved = service.create(r);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> update(@PathVariable Integer id, @RequestBody Rating r) {
        return service.update(id, r).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
