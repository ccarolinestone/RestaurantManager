package com.example.restaurant;

public class Rating {
    private int id;
    private HaveTried haveTried;      // reference to HaveTried
    private int foodRating;          // 1..5
    private int vibeRating;          // 1..5
    private String ratingDescription;

    public Rating() {}

    public Rating(int id, HaveTried haveTried, int foodRating, int vibeRating, String ratingDescription) {
        this.id = id;
        this.haveTried = haveTried;
        this.foodRating = foodRating;
        this.vibeRating = vibeRating;
        this.ratingDescription = ratingDescription;
    }

    public Rating(HaveTried haveTried, int foodRating, int vibeRating, String ratingDescription) {
        this.haveTried = haveTried;
        this.foodRating = foodRating;
        this.vibeRating = vibeRating;
        this.ratingDescription = ratingDescription;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public HaveTried getHaveTried() { return haveTried; }
    public void setHaveTried(HaveTried haveTried) { this.haveTried = haveTried; }
    public int getFoodRating() { return foodRating; }
    public void setFoodRating(int foodRating) { this.foodRating = foodRating; }
    public int getVibeRating() { return vibeRating; }
    public void setVibeRating(int vibeRating) { this.vibeRating = vibeRating; }
    public String getRatingDescription() { return ratingDescription; }
    public void setRatingDescription(String ratingDescription) { this.ratingDescription = ratingDescription; }

    @Override
    public String toString() {
        return "Rating{id=" + id + ", haveTried=" + (haveTried != null ? haveTried.getId() : "null") +
               ", foodRating=" + foodRating + ", vibeRating=" + vibeRating +
               ", ratingDescription='" + ratingDescription + '\'' + '}';
    }
}
