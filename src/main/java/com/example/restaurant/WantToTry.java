package com.example.restaurant;

public class WantToTry {
    private int id;
    private Restaurant restaurant; // Restaurant object

    public WantToTry() {}

    public WantToTry(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public WantToTry(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    @Override
    public String toString() {
        return "WantToTry{id=" + id + ", restaurant=" + (restaurant != null ? restaurant.getId() : "null") + '}';
    }
}
