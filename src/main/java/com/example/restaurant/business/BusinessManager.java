package com.example.restaurant.business;

import com.example.restaurant.model.*;
import com.example.restaurant.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * BusinessManager
 *
 * Central business layer that wraps Spring Data JPA repositories and
 * exposes full CRUD operations for all domain entities:
 *  - Location
 *  - Restaurant
 *  - WantToTry
 *  - HaveTried
 *  - Rating
 *  - Experience
 *  - Dish
 *
 * Design choices:
 *  - Methods return the saved/updated entity for create/update, and the entity or null for reads.
 *  - Delete methods return boolean (true if delete invoked). Repositories do not return boolean,
 *    so a subsequent read is used for verification only if desired.
 *  - All mutating methods are annotated with @Transactional so a service can call multiple
 *    BusinessManager methods in one transaction if desired.
 *  - Exceptions: repository failures throw unchecked DataAccessException (Spring). We do not
 *    declare SQLException here because Spring Data JPA works with unchecked data access exceptions.
 */
@Service
public class BusinessManager {

    // Repositories (one per entity). Assume these exist in com.example.restaurant.repository
    private final LocationRepository locationRepo;
    private final RestaurantRepository restaurantRepo;
    private final WantToTryRepository wantToTryRepo;
    private final HaveTriedRepository haveTriedRepo;
    private final RatingRepository ratingRepo;
    private final ExperienceRepository experienceRepo;
    private final DishRepository dishRepo;

    private static final Set<String> US_STATES = Set.of(
        "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
        "MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
        "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY","DC"
    );

    private void validateStateAbbr(String state) {
        if (state == null) throw new IllegalArgumentException("State is required");
        String up = state.trim().toUpperCase();
        if (!US_STATES.contains(up)) {
            throw new IllegalArgumentException("Invalid US state abbreviation: " + state);
        }
    }
    

    @Autowired
    public BusinessManager(
            LocationRepository locationRepo,
            RestaurantRepository restaurantRepo,
            WantToTryRepository wantToTryRepo,
            HaveTriedRepository haveTriedRepo,
            RatingRepository ratingRepo,
            ExperienceRepository experienceRepo,
            DishRepository dishRepo
    ) {
        this.locationRepo = locationRepo;
        this.restaurantRepo = restaurantRepo;
        this.wantToTryRepo = wantToTryRepo;
        this.haveTriedRepo = haveTriedRepo;
        this.ratingRepo = ratingRepo;
        this.experienceRepo = experienceRepo;
        this.dishRepo = dishRepo;
    }

    // ---------------- Location CRUD ----------------

    @Transactional
    public Location createLocation(Location loc) throws DataAccessException {
        if (loc == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (loc.getCity() == null || loc.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        // normalize and validate state
        String stateNormalized = loc.getState() == null ? null : loc.getState().trim().toUpperCase();
        validateStateAbbr(stateNormalized);
        loc.setState(stateNormalized);

        loc.setId(null); // ensure created as new entity
        return locationRepo.save(loc);
    }

    @Transactional
    public Location updateLocation(Location loc) throws DataAccessException {
        if (loc == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (loc.getId() == null) {
            throw new IllegalArgumentException("Location must have an id to update");
        }
        if (!locationRepo.existsById(loc.getId())) {
            throw new IllegalArgumentException("Location id " + loc.getId() + " does not exist");
        }

        if (loc.getCity() == null || loc.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        // normalize and validate state
        String stateNormalized = loc.getState() == null ? null : loc.getState().trim().toUpperCase();
        validateStateAbbr(stateNormalized);
        loc.setState(stateNormalized);

        // Save will perform the update since id is present
        return locationRepo.save(loc);
    }

    /**
     * Convenience save: create if id==null, otherwise update.
     */
    @Transactional
    public Location saveLocation(Location loc) throws DataAccessException {
        if (loc == null) throw new IllegalArgumentException("Location cannot be null");
        if (loc.getId() == null) return createLocation(loc);
        return updateLocation(loc);
    }

    public Location findLocationById(Integer id) throws DataAccessException {
        if (id == null) return null;
        Optional<Location> o = locationRepo.findById(id);
        return o.orElse(null);
    }

    public List<Location> findAllLocations() throws DataAccessException {
        return locationRepo.findAll();
    }

    @Transactional
    public boolean deleteLocationById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!locationRepo.existsById(id)) return false;
        locationRepo.deleteById(id);
        return true;
    }

    // ---------------- Restaurant CRUD ----------------

    @Transactional
    public Restaurant createRestaurant(Restaurant r) throws DataAccessException {
        r.setId(null);
        return restaurantRepo.save(r);
    }

    @Transactional
    public Restaurant updateRestaurant(Restaurant r) throws DataAccessException {
        if (r == null || r.getId() == null) throw new IllegalArgumentException("Restaurant id required for update");
        if (!restaurantRepo.existsById(r.getId()))
            throw new IllegalArgumentException("Restaurant id " + r.getId() + " does not exist");
        return restaurantRepo.save(r);
    }

    @Transactional
    public Restaurant saveRestaurant(Restaurant r) throws DataAccessException {
        if (r == null) throw new IllegalArgumentException("Restaurant cannot be null");
        return (r.getId() == null) ? createRestaurant(r) : updateRestaurant(r);
    }

    public Restaurant findRestaurantById(Integer id) throws DataAccessException {
        return restaurantRepo.findById(id).orElse(null);
    }

    public List<Restaurant> findAllRestaurants() throws DataAccessException {
        return restaurantRepo.findAll();
    }

    @Transactional
    public boolean deleteRestaurantById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!restaurantRepo.existsById(id)) return false;
        restaurantRepo.deleteById(id);
        return true;
    }

    // ---------------- WantToTry CRUD ----------------

    @Transactional
    public WantToTry createWantToTry(WantToTry w) throws DataAccessException {
        w.setId(null);
        return wantToTryRepo.save(w);
    }

    @Transactional
    public WantToTry updateWantToTry(WantToTry w) throws DataAccessException {
        if (w == null || w.getId() == null) throw new IllegalArgumentException("WantToTry id required for update");
        if (!wantToTryRepo.existsById(w.getId()))
            throw new IllegalArgumentException("WantToTry id " + w.getId() + " does not exist");
        return wantToTryRepo.save(w);
    }

    @Transactional
    public WantToTry saveWantToTry(WantToTry w) throws DataAccessException {
        if (w == null) throw new IllegalArgumentException("WantToTry cannot be null");
        return (w.getId() == null) ? createWantToTry(w) : updateWantToTry(w);
    }

    public WantToTry findWantToTryById(Integer id) throws DataAccessException {
        return wantToTryRepo.findById(id).orElse(null);
    }

    public List<WantToTry> findAllWantToTry() throws DataAccessException {
        return wantToTryRepo.findAll();
    }

    @Transactional
    public boolean deleteWantToTryById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!wantToTryRepo.existsById(id)) return false;
        wantToTryRepo.deleteById(id);
        return true;
    }

    // ---------------- HaveTried CRUD ----------------

    @Transactional
    public HaveTried createHaveTried(HaveTried h) throws DataAccessException {
        h.setId(null);
        return haveTriedRepo.save(h);
    }

    @Transactional
    public HaveTried updateHaveTried(HaveTried h) throws DataAccessException {
        if (h == null || h.getId() == null) throw new IllegalArgumentException("HaveTried id required for update");
        if (!haveTriedRepo.existsById(h.getId()))
            throw new IllegalArgumentException("HaveTried id " + h.getId() + " does not exist");
        return haveTriedRepo.save(h);
    }

    @Transactional
    public HaveTried saveHaveTried(HaveTried h) throws DataAccessException {
        if (h == null) throw new IllegalArgumentException("HaveTried cannot be null");
        return (h.getId() == null) ? createHaveTried(h) : updateHaveTried(h);
    }

    public HaveTried findHaveTriedById(Integer id) throws DataAccessException {
        return haveTriedRepo.findById(id).orElse(null);
    }

    public List<HaveTried> findAllHaveTried() throws DataAccessException {
        return haveTriedRepo.findAll();
    }

    @Transactional
    public boolean deleteHaveTriedById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!haveTriedRepo.existsById(id)) return false;
        haveTriedRepo.deleteById(id);
        return true;
    }

    // ---------------- Rating CRUD ----------------

    @Transactional
    public Rating createRating(Rating r) throws DataAccessException {
        r.setId(null);
        return ratingRepo.save(r);
    }

    @Transactional
    public Rating updateRating(Rating r) throws DataAccessException {
        if (r == null || r.getId() == null) throw new IllegalArgumentException("Rating id required for update");
        if (!ratingRepo.existsById(r.getId()))
            throw new IllegalArgumentException("Rating id " + r.getId() + " does not exist");
        return ratingRepo.save(r);
    }

    @Transactional
    public Rating saveRating(Rating r) throws DataAccessException {
        if (r == null) throw new IllegalArgumentException("Rating cannot be null");
        return (r.getId() == null) ? createRating(r) : updateRating(r);
    }

    public Rating findRatingById(Integer id) throws DataAccessException {
        return ratingRepo.findById(id).orElse(null);
    }

    public List<Rating> findAllRatings() throws DataAccessException {
        return ratingRepo.findAll();
    }

    @Transactional
    public boolean deleteRatingById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!ratingRepo.existsById(id)) return false;
        ratingRepo.deleteById(id);
        return true;
    }

    // ---------------- Experience CRUD ----------------

    @Transactional
    public Experience createExperience(Experience e) throws DataAccessException {
        e.setId(null);
        return experienceRepo.save(e);
    }

    @Transactional
    public Experience updateExperience(Experience e) throws DataAccessException {
        if (e == null || e.getId() == null) throw new IllegalArgumentException("Experience id required for update");
        if (!experienceRepo.existsById(e.getId()))
            throw new IllegalArgumentException("Experience id " + e.getId() + " does not exist");
        return experienceRepo.save(e);
    }

    @Transactional
    public Experience saveExperience(Experience e) throws DataAccessException {
        if (e == null) throw new IllegalArgumentException("Experience cannot be null");
        return (e.getId() == null) ? createExperience(e) : updateExperience(e);
    }

    public Experience findExperienceById(Integer id) throws DataAccessException {
        return experienceRepo.findById(id).orElse(null);
    }

    public List<Experience> findAllExperiences() throws DataAccessException {
        return experienceRepo.findAll();
    }

    @Transactional
    public boolean deleteExperienceById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!experienceRepo.existsById(id)) return false;
        experienceRepo.deleteById(id);
        return true;
    }

    // ---------------- Dish CRUD ----------------

    @Transactional
    public Dish createDish(Dish d) throws DataAccessException {
        d.setId(null);
        return dishRepo.save(d);
    }

    @Transactional
    public Dish updateDish(Integer id, Dish updates) {
        if (id == null) throw new IllegalArgumentException("id required");
        Dish existing = dishRepo.findById(id).orElse(null);
        if (existing == null) throw new IllegalArgumentException("Dish id " + id + " not found");

        if (updates.getDishName() != null) existing.setDishName(updates.getDishName());
        if (updates.getFoodDescription() != null) existing.setFoodDescription(updates.getFoodDescription());
        if (updates.getHaveTried() != null) existing.setHaveTried(updates.getHaveTried());

        return dishRepo.save(existing);
    }

    @Transactional
    public Dish saveDish(Dish d) throws DataAccessException {
        if (d == null) throw new IllegalArgumentException("Dish cannot be null");
        return (d.getId() == null) ? createDish(d) : updateDish(d.getId(), d);
    }

    public Dish findDishById(Integer id) throws DataAccessException {
        return dishRepo.findById(id).orElse(null);
    }

    public List<Dish> findAllDishes() throws DataAccessException {
        return dishRepo.findAll();
    }

    @Transactional
    public boolean deleteDishById(Integer id) throws DataAccessException {
        if (id == null) return false;
        if (!dishRepo.existsById(id)) return false;
        dishRepo.deleteById(id);
        return true;
    }

        // ----- Helper finder methods used by service layer (delegates to repositories) -----

    // Restaurants by location
    public List<Restaurant> findRestaurantsByLocationId(Integer locationId) {
        if (locationId == null) return List.of();
        return restaurantRepo.findByLocationId(locationId);
    }

    // WantToTry by restaurant
    public List<WantToTry> findWantToTryByRestaurantId(Integer restaurantId) {
        if (restaurantId == null) return List.of();
        return wantToTryRepo.findByRestaurantId(restaurantId);
    }

    // HaveTried by restaurant
    public List<HaveTried> findHaveTriedByRestaurantId(Integer restaurantId) {
        if (restaurantId == null) return List.of();
        return haveTriedRepo.findByRestaurantId(restaurantId);
    }

    // Ratings by haveTried
    public List<Rating> findRatingsByHaveTriedId(Integer haveTriedId) {
        if (haveTriedId == null) return List.of();
        return ratingRepo.findByHaveTriedId(haveTriedId);
    }

    // Experiences by haveTried
    public List<Experience> findExperiencesByHaveTriedId(Integer haveTriedId) {
        if (haveTriedId == null) return List.of();
        return experienceRepo.findByHaveTriedId(haveTriedId);
    }

    // Dishes by haveTried
    public List<Dish> findDishesByHaveTriedId(Integer haveTriedId) {
        if (haveTriedId == null) return List.of();
        return dishRepo.findByHaveTriedId(haveTriedId);
    }

    // Convenience overload so services can call updateDish(dish)
    @Transactional
    public Dish updateDish(Dish updates) {
        if (updates == null || updates.getId() == null) {
            throw new IllegalArgumentException("Dish id required for update");
        }
        return updateDish(updates.getId(), updates);
    }
}
