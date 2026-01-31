package com.example.restaurant;

public class Restaurant {
    private int id;
    private String restaurantName;
    private Location location; // Location object
    private String type;       // e.g., dine-in, fast-food
    private String cuisine;    // e.g., Italian, Thai

    public Restaurant() {}

    public Restaurant(int id, String restaurantName, Location location, String type, String cuisine) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.location = location;
        this.type = type;
        this.cuisine = cuisine;
    }

    public Restaurant(String restaurantName, Location location, String type, String cuisine) {
        this.restaurantName = restaurantName;
        this.location = location;
        this.type = type;
        this.cuisine = cuisine;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    @Override
    public String toString() {
        return "Restaurant{id=" + id + ", restaurantName='" + restaurantName + '\'' +
                ", location=" + (location != null ? location.getId() + ":" + location.getCity() : "null") +
                ", type='" + type + '\'' + ", cuisine='" + cuisine + '\'' + '}';
    }
}
