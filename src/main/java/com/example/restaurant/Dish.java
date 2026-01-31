package com.example.restaurant;

public class Dish {
    private int id;
    private HaveTried haveTried;
    private String dishName;
    private String foodDescription;

    public Dish() {}

    public Dish(int id, HaveTried haveTried, String dishName, String foodDescription) {
        this.id = id;
        this.haveTried = haveTried;
        this.dishName = dishName;
        this.foodDescription = foodDescription;
    }

    public Dish(HaveTried haveTried, String dishName, String foodDescription) {
        this.haveTried = haveTried;
        this.dishName = dishName;
        this.foodDescription = foodDescription;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public HaveTried getHaveTried() { return haveTried; }
    public void setHaveTried(HaveTried haveTried) { this.haveTried = haveTried; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public String getFoodDescription() { return foodDescription; }
    public void setFoodDescription(String foodDescription) { this.foodDescription = foodDescription; }

    @Override
    public String toString() {
        return "Dish{id=" + id + ", haveTried=" + (haveTried != null ? haveTried.getId() : "null") +
               ", dishName='" + dishName + '\'' + ", foodDescription='" + foodDescription + '\'' + '}';
    }
}
