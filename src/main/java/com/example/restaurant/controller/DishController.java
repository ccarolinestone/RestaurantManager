package com.example.restaurant.controller;

import com.example.restaurant.model.Dish;
import com.example.restaurant.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable Integer id) {
        return service.findById(id)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-have-tried/{haveTriedId}")
    public List<Dish> byHaveTried(@PathVariable Integer haveTriedId) {
        return service.findByHaveTriedId(haveTriedId);
    }

    @PostMapping
    public ResponseEntity<Dish> create(@RequestBody Dish d) {
        Dish saved = service.create(d);
        return ResponseEntity.ok(saved);
        // Optionally: return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> update(@PathVariable Integer id, @RequestBody Dish d) {
        return service.update(id, d)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

