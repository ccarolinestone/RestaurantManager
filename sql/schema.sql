-- schema.sql
CREATE DATABASE IF NOT EXISTS restaurant_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE restaurant_manager;

-- Location
CREATE TABLE location (
  id INT AUTO_INCREMENT PRIMARY KEY,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(50) NOT NULL
);

-- Restaurant
CREATE TABLE restaurant (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_name VARCHAR(200) NOT NULL,
  location_id INT NOT NULL,
  type VARCHAR(100),
  cuisine VARCHAR(100),
  FOREIGN KEY (location_id) REFERENCES location(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- WantToTry
CREATE TABLE want_to_try (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_id INT NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- HaveTried
CREATE TABLE have_tried (
  id INT AUTO_INCREMENT PRIMARY KEY,
  restaurant_id INT NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Rating (references have_tried)
CREATE TABLE rating (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  food_rating TINYINT NOT NULL CHECK (food_rating BETWEEN 1 AND 5),
  vibe_rating TINYINT NOT NULL CHECK (vibe_rating BETWEEN 1 AND 5),
  rating_description TEXT,
  FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Experience (references have_tried)
CREATE TABLE experience (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  `date` DATE NOT NULL,
  description TEXT,
  FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Dish (references have_tried)
CREATE TABLE dish (
  id INT AUTO_INCREMENT PRIMARY KEY,
  have_tried_id INT NOT NULL,
  dish_name VARCHAR(200) NOT NULL,
  food_description TEXT,
  FOREIGN KEY (have_tried_id) REFERENCES have_tried(id) ON DELETE CASCADE ON UPDATE CASCADE
);
