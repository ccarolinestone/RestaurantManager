# Restaurant Manager

A Java Spring Boot REST API for tracking restaurants, dining experiences, ratings, and dishes.

## Technology Stack

- **Java 17** — core language
- **Spring Boot 3.5** — framework with embedded Tomcat server
- **Spring Data JPA / Hibernate 6** — ORM and data access
- **MySQL 8** — relational database
- **Maven** — build tool
- **Jackson** — JSON serialization

## Database

7 tables: Location, Restaurant, WantToTry, HaveTried, Rating, Experience, Dish. Each table has 50 rows of seed data (350 total). Tables use primary keys, foreign keys, CHECK constraints, and CASCADE deletes.

## Architecture

Console Client → REST Controllers → Service Layer → Business Layer → JPA Repositories → MySQL

## How to Run

### Prerequisites
- Java 17+, Maven 3.8+, MySQL 8

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

### Run the Console Test Client
With the server running in one terminal, open a second terminal:
```bash
mvn -q exec:java -Dexec.mainClass="com.example.restaurant.app.ServiceConsoleClient" -Dexec.classpathScope=runtime
```
This demonstrates full Create, Read, Update, and Delete operations through the REST API.

## API Endpoints

Each entity supports GET, GET/{id}, POST, PUT/{id}, DELETE/{id}:

- `/api/locations`
- `/api/restaurants`
- `/api/wanttotry`
- `/api/havetried`
- `/api/ratings`
- `/api/experiences`
- `/api/dishes`

# Getting Started

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
