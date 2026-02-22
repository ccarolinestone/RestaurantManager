package com.example.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Restaurant.class)
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private String type;
    private String cuisine;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WantToTry> wantToTryList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HaveTried> haveTriedList = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(Integer id, String restaurantName, Location location, String type, String cuisine) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.location = location;
        this.type = type;
        this.cuisine = cuisine;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public List<WantToTry> getWantToTryList() { return wantToTryList; }
    public void setWantToTryList(List<WantToTry> wantToTryList) { this.wantToTryList = wantToTryList; }
    public List<HaveTried> getHaveTriedList() { return haveTriedList; }
    public void setHaveTriedList(List<HaveTried> haveTriedList) { this.haveTriedList = haveTriedList; }
}