# Restaurant Manager

A Java Spring Boot REST API for tracking restaurants, dining experiences, ratings, and dishes — with a standalone web client.

## Technology Stack

- **Java 17** — core language
- **Spring Boot 3.5** — framework with embedded Tomcat server
- **Spring Data JPA / Hibernate 6** — ORM and data access
- **MySQL 8** — relational database
- **Maven** — build tool
- **Jackson** — JSON serialization
- **HTML / CSS / JavaScript** — standalone web client
- **Fetch API** — client-side HTTP requests to REST controllers

## Database

7 tables: Location, Restaurant, WantToTry, HaveTried, Rating, Experience, Dish. Each table has 50 rows of seed data (350 total). Tables use primary keys, foreign keys, CHECK constraints, and CASCADE deletes.

## Architecture

```
Web Client (index.html) → fetch() HTTP requests
        ↓
REST Controllers (/api/...)
        ↓
Service Layer
        ↓
Business Layer
        ↓
JPA Repositories
        ↓
MySQL Database
```

The web client is a standalone HTML file that communicates with the Spring Boot REST API using the JavaScript `fetch()` API. Every operation goes through the full layer chain — no layers are bypassed.

## How to Run

### Prerequisites
- Java 17+, Maven 3.8+, MySQL 8, a web browser (Chrome, Firefox, Edge, etc.)

### Database Setup
```bash
mysql -u root -p
```
```sql
CREATE DATABASE IF NOT EXISTS restaurant_manager;
```
Then load the schema and seed data:
```bash
mysql -u root -p restaurant_manager < src/main/resources/schema.sql
mysql -u root -p restaurant_manager < src/main/resources/data.sql
```
Update `src/main/resources/application.properties` with your MySQL credentials.

### Start the Server
```bash
mvn -DskipTests clean package
mvn spring-boot:run
```
The API runs on **http://localhost:8080**. All endpoints are under `/api/`.

### Host and Run the Web Client (Project 3)

The web client is hosted by the Spring Boot embedded Tomcat server. The file `index.html` is located in `src/main/resources/static/`, which Spring Boot automatically serves as a static web page.

1. Make sure `index.html` is in `src/main/resources/static/`:
   ```bash
   ls src/main/resources/static/index.html
   ```
   If it is not there, copy it:
   ```bash
   cp index.html src/main/resources/static/index.html
   ```

2. Build and start the Spring Boot server:
   ```bash
   mvn -DskipTests clean package
   mvn spring-boot:run
   ```

3. Open your web browser and go to:
   ```
   http://localhost:8080
   ```

4. Click **"Fetch All"** to load all data from the API.

The web client is now hosted on the Spring Boot embedded Tomcat server and accessible as a web page at `http://localhost:8080`.

### How to Use the Web Client
- **Tabs**: Click the tabs at the top (Locations, Restaurants, Want To Try, Have Tried, Ratings, Experiences, Dishes) to switch between entities.
- **View all records**: Click "Fetch All" or "Show All" on any tab to retrieve all records for that entity.
- **Search by ID**: Enter an ID number in the "Search by ID" field and click "Search" to retrieve a single record.
- **Filter by parent**: Use the filter dropdown (e.g., "Filter by Location", "Filter by Restaurant", "Filter by Have Tried") and click "Search" to retrieve a subset of records.
- **Create**: Fill out the "Add New" form at the top of each tab and click "Create".
- **Edit**: Click the "Edit" button on any row, modify the values in the inline edit form, and click "Save".
- **Delete**: Click the "Delete" button on any row and confirm the deletion.
- **Clear**: Click "Clear Tables" to clear all displayed data from the page.

### CORS Configuration
The file `src/main/java/com/example/restaurant/config/CorsConfig.java` enables cross-origin requests so the web client can communicate with the REST API.

### Run the Console Test Client (Project 2)
With the server running in one terminal, open a second terminal:
```bash
mvn -q exec:java -Dexec.mainClass="com.example.restaurant.app.ServiceConsoleClient" -Dexec.classpathScope=runtime
```
This demonstrates full Create, Read, Update, and Delete operations through the REST API.

## API Endpoints

Each entity supports GET, GET/{id}, POST, PUT/{id}, DELETE/{id}:

- `/api/locations`
- `/api/restaurants` (also: `/api/restaurants/by-location/{locationId}`)
- `/api/wanttotry` (also: `/api/wanttotry/by-restaurant/{restaurantId}`)
- `/api/havetried` (also: `/api/havetried/by-restaurant/{restaurantId}`)
- `/api/ratings` (also: `/api/ratings/by-havetried/{haveTriedId}`)
- `/api/experiences` (also: `/api/experiences/by-havetried/{haveTriedId}`)
- `/api/dishes` (also: `/api/dishes/by-have-tried/{haveTriedId}`)

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.11/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.11/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.11/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.11/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/3.5.11/reference/io/validation.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.