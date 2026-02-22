package com.example.restaurant.controller;

import com.example.restaurant.model.Experience;
import com.example.restaurant.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService service;

    public ExperienceController(ExperienceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Experience> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> get(@PathVariable Integer id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-havetried/{haveTriedId}")
    public List<Experience> byHaveTried(@PathVariable Integer haveTriedId) {
        return service.findByHaveTriedId(haveTriedId);
    }

    @PostMapping
    public ResponseEntity<Experience> create(@RequestBody Experience e) {
        Experience saved = service.create(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> update(@PathVariable Integer id, @RequestBody Experience e) {
        return service.update(id, e).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

