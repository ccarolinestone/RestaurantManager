package com.example.restaurant;

public class HaveTried {
    private int id;
    private Restaurant restaurant;

    public HaveTried() {}

    public HaveTried(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public HaveTried(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    @Override
    public String toString() {
        return "HaveTried{id=" + id + ", restaurant=" + (restaurant != null ? restaurant.getId() : "null") + '}';
    }
}
