package com.example.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Rating.class)
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "have_tried_id", nullable = false)
    private HaveTried haveTried;

    @Column(name = "food_rating")
    private Integer foodRating;

    @Column(name = "vibe_rating")
    private Integer vibeRating;

    @Column(name = "rating_description")
    private String ratingDescription;

    public Rating() {}

    public Rating(Integer id, HaveTried haveTried, Integer foodRating, Integer vibeRating, String ratingDescription) {
        this.id = id;
        this.haveTried = haveTried;
        this.foodRating = foodRating;
        this.vibeRating = vibeRating;
        this.ratingDescription = ratingDescription;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public HaveTried getHaveTried() { return haveTried; }
    public void setHaveTried(HaveTried haveTried) { this.haveTried = haveTried; }
    public Integer getFoodRating() { return foodRating; }
    public void setFoodRating(Integer foodRating) { this.foodRating = foodRating; }
    public Integer getVibeRating() { return vibeRating; }
    public void setVibeRating(Integer vibeRating) { this.vibeRating = vibeRating; }
    public String getRatingDescription() { return ratingDescription; }
    public void setRatingDescription(String ratingDescription) { this.ratingDescription = ratingDescription; }
}