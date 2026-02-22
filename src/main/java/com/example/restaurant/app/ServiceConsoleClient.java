package com.example.restaurant.app;

import com.example.restaurant.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Simple console client that calls the REST APIs and demonstrates a full lifecycle:
 * create Location -> create Restaurant (using only location id) -> get Restaurant -> create HaveTried (using only restaurant id)
 * -> create Rating, Experience, Dish (using only haveTried id) -> update Restaurant -> delete HaveTried -> delete Restaurant -> verify deletion.
 *
 * Run this AFTER the Spring Boot app is running (http://localhost:8080).
 *
 * Note: To avoid Jackson ObjectId / identity-binding conflicts we always send reference objects that contain ONLY the id
 * when referencing an entity that already exists on the server.
 */
public class ServiceConsoleClient {

    private static final String BASE = "http://localhost:8080/api";
    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // support java.time types
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static void main(String[] args) {
        System.out.println("=== ServiceConsoleClient starting ===\n");
        try {
            // -------- CREATE --------
            System.out.println("--- Step 1: CREATE entities ---");

            // Create Location
            Location loc = new Location();
            loc.setCity("ClientCity");
            loc.setState("NY");
            Location savedLoc = post("/locations", loc, Location.class);
            System.out.println("Created Location (id=" + savedLoc.getId() + ")");

            // Create Restaurant
            Restaurant rest = new Restaurant();
            rest.setRestaurantName("Client Cafe");
            rest.setType("Cafe");
            rest.setCuisine("Fusion");
            Location locRef = new Location();
            locRef.setId(savedLoc.getId());
            rest.setLocation(locRef);
            Restaurant savedRest = post("/restaurants", rest, Restaurant.class);
            System.out.println("Created Restaurant (id=" + savedRest.getId() + ")");

            // Create HaveTried
            HaveTried ht = new HaveTried();
            Restaurant restRef = new Restaurant();
            restRef.setId(savedRest.getId());
            ht.setRestaurant(restRef);
            HaveTried savedHt = post("/havetried", ht, HaveTried.class);
            System.out.println("Created HaveTried (id=" + savedHt.getId() + ")");

            // Create Rating
            HaveTried htRef = new HaveTried();
            htRef.setId(savedHt.getId());
            Rating rating = new Rating();
            rating.setHaveTried(htRef);
            rating.setFoodRating(5);
            rating.setVibeRating(4);
            rating.setRatingDescription("Tasty via client");
            Rating savedRating = post("/ratings", rating, Rating.class);
            System.out.println("Created Rating (id=" + savedRating.getId() + ")");

            // Create Experience
            Experience ex = new Experience();
            ex.setHaveTried(htRef);
            ex.setDate(LocalDate.now());
            ex.setDescription("Client demo experience");
            Experience savedExp = post("/experiences", ex, Experience.class);
            System.out.println("Created Experience (id=" + savedExp.getId() + ")");

            // Create Dish
            Dish dish = new Dish();
            dish.setHaveTried(htRef);
            dish.setDishName("Client Special");
            dish.setFoodDescription("Delicious sample");
            Dish savedDish = post("/dishes", dish, Dish.class);
            System.out.println("Created Dish (id=" + savedDish.getId() + ")");

            // -------- GET (verify creates) --------
            System.out.println("\n--- Step 2: GET entities (verify creates) ---");

            Restaurant fetchedRest = get("/restaurants/" + savedRest.getId(), Restaurant.class);
            System.out.println("GET Restaurant: id=" + fetchedRest.getId() + ", name=" + fetchedRest.getRestaurantName());

            HaveTried fetchedHt = get("/havetried/" + savedHt.getId(), HaveTried.class);
            System.out.println("GET HaveTried: id=" + fetchedHt.getId());

            Rating fetchedRating = get("/ratings/" + savedRating.getId(), Rating.class);
            System.out.println("GET Rating: id=" + fetchedRating.getId() + ", food=" + fetchedRating.getFoodRating() + ", vibe=" + fetchedRating.getVibeRating());

            Experience fetchedExp = get("/experiences/" + savedExp.getId(), Experience.class);
            System.out.println("GET Experience: id=" + fetchedExp.getId() + ", date=" + fetchedExp.getDate());

            Dish fetchedDish = get("/dishes/" + savedDish.getId(), Dish.class);
            System.out.println("GET Dish: id=" + fetchedDish.getId() + ", name=" + fetchedDish.getDishName());

            // -------- UPDATE --------
            System.out.println("\n--- Step 3: UPDATE Restaurant ---");

            fetchedRest.setRestaurantName(fetchedRest.getRestaurantName() + " UPDATED");
            Location fetchedLocRef = new Location();
            fetchedLocRef.setId(fetchedRest.getLocation().getId());
            fetchedRest.setLocation(fetchedLocRef);
            Restaurant updated = put("/restaurants/" + fetchedRest.getId(), fetchedRest, Restaurant.class);
            System.out.println("Updated Restaurant: id=" + updated.getId() + ", name=" + updated.getRestaurantName());

            // GET after update
            Restaurant afterUpdate = get("/restaurants/" + updated.getId(), Restaurant.class);
            System.out.println("GET after update: id=" + afterUpdate.getId() + ", name=" + afterUpdate.getRestaurantName());

            // -------- DELETE --------
            System.out.println("\n--- Step 4: DELETE entities ---");

            // Delete HaveTried (cascades to Rating, Experience, Dish)
            boolean deletedHt = delete("/havetried/" + savedHt.getId());
            System.out.println("Deleted HaveTried (id=" + savedHt.getId() + "): " + deletedHt);

            // Verify HaveTried deletion
            HttpResponse<String> htAfter = rawGet("/havetried/" + savedHt.getId());
            System.out.println("GET HaveTried after delete: " + htAfter.statusCode() + " (404 expected)");

            // Verify cascaded deletions
            HttpResponse<String> ratingAfter = rawGet("/ratings/" + savedRating.getId());
            System.out.println("GET Rating after delete: " + ratingAfter.statusCode() + " (404 expected)");

            HttpResponse<String> expAfter = rawGet("/experiences/" + savedExp.getId());
            System.out.println("GET Experience after delete: " + expAfter.statusCode() + " (404 expected)");

            HttpResponse<String> dishAfter = rawGet("/dishes/" + savedDish.getId());
            System.out.println("GET Dish after delete: " + dishAfter.statusCode() + " (404 expected)");

            // Delete Restaurant
            boolean deletedRest = delete("/restaurants/" + savedRest.getId());
            System.out.println("Deleted Restaurant (id=" + savedRest.getId() + "): " + deletedRest);

            // Verify Restaurant deletion
            HttpResponse<String> restAfter = rawGet("/restaurants/" + savedRest.getId());
            System.out.println("GET Restaurant after delete: " + restAfter.statusCode() + " (404 expected)");

            // Delete Location
            boolean deletedLoc = delete("/locations/" + savedLoc.getId());
            System.out.println("Deleted Location (id=" + savedLoc.getId() + "): " + deletedLoc);

            // Verify Location deletion
            HttpResponse<String> locAfter = rawGet("/locations/" + savedLoc.getId());
            System.out.println("GET Location after delete: " + locAfter.statusCode() + " (404 expected)");

            System.out.println("\n=== Demo complete ===");
        } catch (Exception e) {
            System.err.println("ERROR during demo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------ HTTP helper methods ------------

    private static ObjectMapper readerMapper() {
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        m.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return m;
    }

    private static <T> T post(String path, Object body, Class<T> clazz) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(body);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> r = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (r.statusCode() >= 200 && r.statusCode() < 300 && r.body() != null && !r.body().isBlank()) {
            return readerMapper().readValue(r.body(), clazz);
        } else if (r.statusCode() >= 200 && r.statusCode() < 300) {
            return null;
        } else {
            throw new RuntimeException("POST failed: " + r.statusCode() + " -> " + r.body());
        }
    }

    private static <T> T get(String path, Class<T> clazz) throws IOException, InterruptedException {
        HttpResponse<String> r = rawGet(path);
        if (r.statusCode() == 200 && r.body() != null && !r.body().isBlank()) {
            return readerMapper().readValue(r.body(), clazz);
        } else {
            return null;
        }
    }

    private static HttpResponse<String> rawGet(String path) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .GET()
                .build();
        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private static <T> T put(String path, Object body, Class<T> clazz) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(body);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> r = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (r.statusCode() >= 200 && r.statusCode() < 300 && r.body() != null && !r.body().isBlank()) {
            return readerMapper().readValue(r.body(), clazz);
        } else if (r.statusCode() >= 200 && r.statusCode() < 300) {
            return null;
        } else {
            throw new RuntimeException("PUT failed: " + r.statusCode() + " -> " + r.body());
        }
    }

    private static boolean delete(String path) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .DELETE()
                .build();
        HttpResponse<String> r = http.send(req, HttpResponse.BodyHandlers.ofString());
        return r.statusCode() == 204;
    }
}

