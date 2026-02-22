-- src/main/resources/schema.sql
-- Idempotent schema for Restaurant Manager
-- Note: do NOT include CREATE DATABASE or USE statements here when Spring runs scripts.

-- Location
CREATE TABLE IF NOT EXISTS location (
  id INT AUTO_INCREMENT PRIMARY KEY,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(50) NOT NULL
);

-- Restaurant
CREATE TABLE IF NOT EXISTS restaurant (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_name VARCHAR(200) NOT NULL,
  location_id INT NOT NULL,
  type VARCHAR(100),
  cuisine VARCHAR(100),
  CONSTRAINT fk_restaurant_location FOREIGN KEY (location_id) REFERENCES location(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- WantToTry
CREATE TABLE IF NOT EXISTS want_to_try (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_id INT NOT NULL,
  CONSTRAINT fk_want_to_try_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- HaveTried
CREATE TABLE IF NOT EXISTS have_tried (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_id INT NOT NULL,
  CONSTRAINT fk_have_tried_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Rating (references have_tried)
CREATE TABLE IF NOT EXISTS rating (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  food_rating TINYINT NOT NULL CHECK (food_rating BETWEEN 1 AND 5),
  vibe_rating TINYINT NOT NULL CHECK (vibe_rating BETWEEN 1 AND 5),
  rating_description TEXT,
  CONSTRAINT fk_rating_have_tried FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Experience (references have_tried)
CREATE TABLE IF NOT EXISTS experience (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  `date` DATE NOT NULL,
  description TEXT,
  CONSTRAINT fk_experience_have_tried FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Dish (references have_tried)
CREATE TABLE IF NOT EXISTS dish (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  dish_name VARCHAR(200) NOT NULL,
  food_description TEXT,
  CONSTRAINT fk_dish_have_tried FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Indexes (optional)
CREATE INDEX IF NOT EXISTS idx_restaurant_location ON restaurant(location_id);
CREATE INDEX IF NOT EXISTS idx_want_to_try_restaurant ON want_to_try(restaurant_id);
CREATE INDEX IF NOT EXISTS idx_have_tried_restaurant ON have_tried(restaurant_id);
CREATE INDEX IF NOT EXISTS idx_rating_have_tried ON rating(have_tried_id);
CREATE INDEX IF NOT EXISTS idx_experience_have_tried ON experience(have_tried_id);
CREATE INDEX IF NOT EXISTS idx_dish_have_tried ON dish(have_tried_id);