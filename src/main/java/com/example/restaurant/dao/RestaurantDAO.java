package com.example.restaurant.dao;

import com.example.restaurant.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JDBC DAO for Restaurant Manager.
 * Adjust DB_URL, USER, PASS before running.
 */
public class RestaurantDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_manager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "your_db_user";
    private static final String PASS = "your_db_password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // -------------------
    // Location CRUD
    // -------------------
    public Location createLocation(Location loc) throws SQLException {
        String sql = "INSERT INTO location (city, state) VALUES (?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, loc.getCity());
            ps.setString(2, loc.getState());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    loc.setId(rs.getInt(1));
                }
            }
        }
        return loc;
    }

    public Location getLocationById(int id) throws SQLException {
        String sql = "SELECT id, city, state FROM location WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Location(rs.getInt("id"), rs.getString("city"), rs.getString("state"));
                }
            }
        }
        return null;
    }

    public List<Location> listAllLocations() throws SQLException {
        String sql = "SELECT id, city, state FROM location";
        List<Location> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Location(rs.getInt("id"), rs.getString("city"), rs.getString("state")));
            }
        }
        return list;
    }

    public boolean updateLocation(Location loc) throws SQLException {
        String sql = "UPDATE location SET city=?, state=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loc.getCity());
            ps.setString(2, loc.getState());
            ps.setInt(3, loc.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteLocation(int id) throws SQLException {
        String sql = "DELETE FROM location WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // Restaurant CRUD
    // -------------------
    public Restaurant createRestaurant(Restaurant r) throws SQLException {
        String sql = "INSERT INTO restaurant (restaurant_name, location_id, type, cuisine) VALUES (?,?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getRestaurantName());
            ps.setInt(2, r.getLocation().getId());
            ps.setString(3, r.getType());
            ps.setString(4, r.getCuisine());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getInt(1));
            }
        }
        return r;
    }

    public Restaurant getRestaurantById(int id) throws SQLException {
        String sql = "SELECT r.id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM restaurant r JOIN location l ON r.location_id = l.id WHERE r.id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                    return new Restaurant(rs.getInt("id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine"));
                }
            }
        }
        return null;
    }

    public List<Restaurant> listAllRestaurants() throws SQLException {
        String sql = "SELECT r.id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM restaurant r JOIN location l ON r.location_id = l.id";
        List<Restaurant> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                list.add(new Restaurant(rs.getInt("id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine")));
            }
        }
        return list;
    }

    public boolean updateRestaurant(Restaurant r) throws SQLException {
        String sql = "UPDATE restaurant SET restaurant_name=?, location_id=?, type=?, cuisine=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getRestaurantName());
            ps.setInt(2, r.getLocation().getId());
            ps.setString(3, r.getType());
            ps.setString(4, r.getCuisine());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRestaurant(int id) throws SQLException {
        String sql = "DELETE FROM restaurant WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // WantToTry CRUD
    // -------------------
    public WantToTry createWantToTry(WantToTry w) throws SQLException {
        String sql = "INSERT INTO want_to_try (restaurant_id) VALUES (?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, w.getRestaurant().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) w.setId(rs.getInt(1));
            }
        }
        return w;
    }

    public WantToTry getWantToTryById(int id) throws SQLException {
        String sql = "SELECT w.id, w.restaurant_id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM want_to_try w JOIN restaurant r ON w.restaurant_id = r.id JOIN location l ON r.location_id = l.id WHERE w.id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                    Restaurant r = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine"));
                    return new WantToTry(rs.getInt("id"), r);
                }
            }
        }
        return null;
    }

    public List<WantToTry> listAllWantToTry() throws SQLException {
        String sql = "SELECT w.id, w.restaurant_id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM want_to_try w JOIN restaurant r ON w.restaurant_id = r.id JOIN location l ON r.location_id = l.id";
        List<WantToTry> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                Restaurant r = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine"));
                list.add(new WantToTry(rs.getInt("id"), r));
            }
        }
        return list;
    }

    public boolean updateWantToTry(WantToTry w) throws SQLException {
        String sql = "UPDATE want_to_try SET restaurant_id=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, w.getRestaurant().getId());
            ps.setInt(2, w.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteWantToTry(int id) throws SQLException {
        String sql = "DELETE FROM want_to_try WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // HaveTried CRUD
    // -------------------
    public HaveTried createHaveTried(HaveTried h) throws SQLException {
        String sql = "INSERT INTO have_tried (restaurant_id) VALUES (?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, h.getRestaurant().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) h.setId(rs.getInt(1));
            }
        }
        return h;
    }

    public HaveTried getHaveTriedById(int id) throws SQLException {
        String sql = "SELECT h.id, h.restaurant_id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM have_tried h JOIN restaurant r ON h.restaurant_id = r.id JOIN location l ON r.location_id = l.id WHERE h.id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                    Restaurant r = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine"));
                    return new HaveTried(rs.getInt("id"), r);
                }
            }
        }
        return null;
    }

    public List<HaveTried> listAllHaveTried() throws SQLException {
        String sql = "SELECT h.id, h.restaurant_id, r.restaurant_name, r.location_id, r.type, r.cuisine, l.city, l.state " +
                     "FROM have_tried h JOIN restaurant r ON h.restaurant_id = r.id JOIN location l ON r.location_id = l.id";
        List<HaveTried> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Location loc = new Location(rs.getInt("location_id"), rs.getString("city"), rs.getString("state"));
                Restaurant r = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"), loc, rs.getString("type"), rs.getString("cuisine"));
                list.add(new HaveTried(rs.getInt("id"), r));
            }
        }
        return list;
    }

    public boolean updateHaveTried(HaveTried h) throws SQLException {
        String sql = "UPDATE have_tried SET restaurant_id=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, h.getRestaurant().getId());
            ps.setInt(2, h.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteHaveTried(int id) throws SQLException {
        String sql = "DELETE FROM have_tried WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // Rating CRUD
    // -------------------
    public Rating createRating(Rating r) throws SQLException {
        String sql = "INSERT INTO rating (have_tried_id, food_rating, vibe_rating, rating_description) VALUES (?,?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getHaveTried().getId());
            ps.setInt(2, r.getFoodRating());
            ps.setInt(3, r.getVibeRating());
            ps.setString(4, r.getRatingDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getInt(1));
            }
        }
        return r;
    }

    public Rating getRatingById(int id) throws SQLException {
        String sql = "SELECT id, have_tried_id, food_rating, vibe_rating, rating_description FROM rating WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HaveTried ht = new HaveTried();
                    ht.setId(rs.getInt("have_tried_id"));
                    return new Rating(rs.getInt("id"), ht, rs.getInt("food_rating"), rs.getInt("vibe_rating"), rs.getString("rating_description"));
                }
            }
        }
        return null;
    }

    public List<Rating> listAllRatings() throws SQLException {
        String sql = "SELECT id, have_tried_id, food_rating, vibe_rating, rating_description FROM rating";
        List<Rating> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HaveTried ht = new HaveTried();
                ht.setId(rs.getInt("have_tried_id"));
                list.add(new Rating(rs.getInt("id"), ht, rs.getInt("food_rating"), rs.getInt("vibe_rating"), rs.getString("rating_description")));
            }
        }
        return list;
    }

    public boolean updateRating(Rating r) throws SQLException {
        String sql = "UPDATE rating SET have_tried_id=?, food_rating=?, vibe_rating=?, rating_description=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, r.getHaveTried().getId());
            ps.setInt(2, r.getFoodRating());
            ps.setInt(3, r.getVibeRating());
            ps.setString(4, r.getRatingDescription());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRating(int id) throws SQLException {
        String sql = "DELETE FROM rating WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // Experience CRUD
    // -------------------
    public Experience createExperience(Experience e) throws SQLException {
        String sql = "INSERT INTO experience (have_tried_id, `date`, description) VALUES (?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, e.getHaveTried().getId());
            ps.setDate(2, Date.valueOf(e.getDate()));
            ps.setString(3, e.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
        return e;
    }

    public Experience getExperienceById(int id) throws SQLException {
        String sql = "SELECT id, have_tried_id, `date`, description FROM experience WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HaveTried ht = new HaveTried();
                    ht.setId(rs.getInt("have_tried_id"));
                    LocalDate d = rs.getDate("date").toLocalDate();
                    return new Experience(rs.getInt("id"), ht, d, rs.getString("description"));
                }
            }
        }
        return null;
    }

    public List<Experience> listAllExperiences() throws SQLException {
        String sql = "SELECT id, have_tried_id, `date`, description FROM experience";
        List<Experience> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HaveTried ht = new HaveTried();
                ht.setId(rs.getInt("have_tried_id"));
                LocalDate d = rs.getDate("date").toLocalDate();
                list.add(new Experience(rs.getInt("id"), ht, d, rs.getString("description")));
            }
        }
        return list;
    }

    public boolean updateExperience(Experience e) throws SQLException {
        String sql = "UPDATE experience SET have_tried_id=?, `date`=?, description=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, e.getHaveTried().getId());
            ps.setDate(2, Date.valueOf(e.getDate()));
            ps.setString(3, e.getDescription());
            ps.setInt(4, e.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteExperience(int id) throws SQLException {
        String sql = "DELETE FROM experience WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------
    // Dish CRUD
    // -------------------
    public Dish createDish(Dish d) throws SQLException {
        String sql = "INSERT INTO dish (have_tried_id, dish_name, food_description) VALUES (?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getHaveTried().getId());
            ps.setString(2, d.getDishName());
            ps.setString(3, d.getFoodDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        }
        return d;
    }

    public Dish getDishById(int id) throws SQLException {
        String sql = "SELECT id, have_tried_id, dish_name, food_description FROM dish WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HaveTried ht = new HaveTried();
                    ht.setId(rs.getInt("have_tried_id"));
                    return new Dish(rs.getInt("id"), ht, rs.getString("dish_name"), rs.getString("food_description"));
                }
            }
        }
        return null;
    }

    public List<Dish> listAllDishes() throws SQLException {
        String sql = "SELECT id, have_tried_id, dish_name, food_description FROM dish";
        List<Dish> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HaveTried ht = new HaveTried();
                ht.setId(rs.getInt("have_tried_id"));
                list.add(new Dish(rs.getInt("id"), ht, rs.getString("dish_name"), rs.getString("food_description")));
            }
        }
        return list;
    }

    public boolean updateDish(Dish d) throws SQLException {
        String sql = "UPDATE dish SET have_tried_id=?, dish_name=?, food_description=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getHaveTried().getId());
            ps.setString(2, d.getDishName());
            ps.setString(3, d.getFoodDescription());
            ps.setInt(4, d.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteDish(int id) throws SQLException {
        String sql = "DELETE FROM dish WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
