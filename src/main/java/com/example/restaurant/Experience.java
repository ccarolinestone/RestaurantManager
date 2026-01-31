package com.example.restaurant;

import java.time.LocalDate;

public class Experience {
    private int id;
    private HaveTried haveTried;
    private LocalDate date;
    private String description;

    public Experience() {}

    public Experience(int id, HaveTried haveTried, LocalDate date, String description) {
        this.id = id;
        this.haveTried = haveTried;
        this.date = date;
        this.description = description;
    }

    public Experience(HaveTried haveTried, LocalDate date, String description) {
        this.haveTried = haveTried;
        this.date = date;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public HaveTried getHaveTried() { return haveTried; }
    public void setHaveTried(HaveTried haveTried) { this.haveTried = haveTried; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Experience{id=" + id + ", haveTried=" + (haveTried != null ? haveTried.getId() : "null") +
               ", date=" + date + ", description='" + description + '\'' + '}';
    }
}
