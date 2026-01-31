package com.example.restaurant;

public class Location {
    private int id;
    private String city;
    private String state;

    public Location() {}

    public Location(int id, String city, String state) {
        this.id = id;
        this.city = city;
        this.state = state;
    }

    public Location(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @Override
    public String toString() {
        return "Location{id=" + id + ", city='" + city + '\'' + ", state='" + state + '\'' + '}';
    }
}
