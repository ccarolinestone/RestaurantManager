package com.example.restaurant.app;

import com.example.restaurant.*;
import com.example.restaurant.dao.RestaurantDAO;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final RestaurantDAO dao = new RestaurantDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // ---- Shutdown hook (last-resort cleanup) ----
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
                System.out.println("MySQL cleanup thread shut down (hook).");
            } catch (Throwable ignored) {}

            try {
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    try {
                        DriverManager.deregisterDriver(drivers.nextElement());
                    } catch (SQLException ignored) {}
                }
                System.out.println("JDBC drivers deregistered (hook).");
            } catch (Throwable ignored) {}
        }));
        // --------------------------------------------

        System.out.println("Restaurant Manager Test Harness");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        listLocations();
                        break;
                    case "2":
                        listRestaurants();
                        break;
                    case "3":
                        listWantToTry();
                        break;
                    case "4":
                        listHaveTried();
                        break;
                    case "5":
                        listRatings();
                        break;
                    case "6":
                        listExperiences();
                        break;
                    case "7":
                        listDishes();
                        break;
                    case "8":
                        createSampleLocationRestaurant();
                        break;
                    case "9":
                        createSampleHaveTriedRatingDishExperience();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exiting.");

                        // Explicit cleanup before Maven exec stops JVM
                        try {
                            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
                            System.out.println("AbandonedConnectionCleanupThread shut down.");
                        } catch (Throwable ignored) {}

                        try {
                            Enumeration<Driver> drivers = DriverManager.getDrivers();
                            while (drivers.hasMoreElements()) {
                                try {
                                    DriverManager.deregisterDriver(drivers.nextElement());
                                } catch (SQLException ignored) {}
                            }
                            System.out.println("JDBC drivers deregistered.");
                        } catch (Throwable ignored) {}
                        break;

                    default:
                        System.out.println("Unknown option.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\nOptions:");
        System.out.println("1 - List Locations");
        System.out.println("2 - List Restaurants");
        System.out.println("3 - List WantToTry");
        System.out.println("4 - List HaveTried");
        System.out.println("5 - List Ratings");
        System.out.println("6 - List Experiences");
        System.out.println("7 - List Dishes");
        System.out.println("8 - Create sample Location + Restaurant");
        System.out.println("9 - Create sample HaveTried + Rating + Dish + Experience for existing restaurant");
        System.out.println("0 - Exit");
        System.out.print("Choose: ");
    }

    private static void listLocations() throws SQLException {
        List<Location> list = dao.listAllLocations();
        System.out.println("--- Locations (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listRestaurants() throws SQLException {
        List<Restaurant> list = dao.listAllRestaurants();
        System.out.println("--- Restaurants (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listWantToTry() throws SQLException {
        List<WantToTry> list = dao.listAllWantToTry();
        System.out.println("--- WantToTry (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listHaveTried() throws SQLException {
        List<HaveTried> list = dao.listAllHaveTried();
        System.out.println("--- HaveTried (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listRatings() throws SQLException {
        List<Rating> list = dao.listAllRatings();
        System.out.println("--- Ratings (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listExperiences() throws SQLException {
        List<Experience> list = dao.listAllExperiences();
        System.out.println("--- Experiences (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void listDishes() throws SQLException {
        List<Dish> list = dao.listAllDishes();
        System.out.println("--- Dishes (" + list.size() + ") ---");
        list.forEach(System.out::println);
    }

    private static void createSampleLocationRestaurant() throws SQLException {
        System.out.print("City: ");
        String city = sc.nextLine().trim();

        System.out.print("State: ");
        String state = sc.nextLine().trim();

        Location loc = new Location(city, state);
        dao.createLocation(loc);
        System.out.println("Created location id=" + loc.getId());

        System.out.print("Restaurant name: ");
        String name = sc.nextLine().trim();

        System.out.print("Type: ");
        String type = sc.nextLine().trim();

        System.out.print("Cuisine: ");
        String cuisine = sc.nextLine().trim();

        Restaurant r = new Restaurant(name, loc, type, cuisine);
        dao.createRestaurant(r);
        System.out.println("Created restaurant id=" + r.getId());
    }

    private static void createSampleHaveTriedRatingDishExperience() throws SQLException {
        System.out.print("Enter restaurant id to mark as tried: ");
        int restId = Integer.parseInt(sc.nextLine().trim());

        Restaurant r = dao.getRestaurantById(restId);
        if (r == null) {
            System.out.println("Restaurant not found.");
            return;
        }

        HaveTried ht = new HaveTried(r);
        dao.createHaveTried(ht);
        System.out.println("Created HaveTried id=" + ht.getId());

        System.out.print("Food rating (1-5): ");
        int food = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Vibe rating (1-5): ");
        int vibe = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Rating description: ");
        String desc = sc.nextLine().trim();

        Rating rating = new Rating(ht, food, vibe, desc);
        dao.createRating(rating);
        System.out.println("Created rating id=" + rating.getId());

        System.out.print("Dish name: ");
        String dishName = sc.nextLine().trim();

        System.out.print("Dish description: ");
        String dishDesc = sc.nextLine().trim();

        Dish dish = new Dish(ht, dishName, dishDesc);
        dao.createDish(dish);
        System.out.println("Created dish id=" + dish.getId());

        System.out.print("Experience date (YYYY-MM-DD) or blank for today: ");
        String dateInput = sc.nextLine().trim();
        LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

        System.out.print("Experience description (or blank for default): ");
        String expDesc = sc.nextLine().trim();
        if (expDesc.isEmpty()) expDesc = "Created via console test.";

        Experience exp = new Experience(ht, date, expDesc);
        dao.createExperience(exp);
        System.out.println("Created experience id=" + exp.getId());
    }
}
