# Restaurant Manager

The Restaurant Manager project is used to keep track of restaurants that users have visited and restaurants that users are interested in visited. Users are able to add locations, information about restaurants, restaurants they would like to try, restaurants they have tried, dishes eaten at restaurants, and their general experiences that those restaurants.

## Technology Used In the Project

- **Java 17+** 
- **Spring Boot 3.5.11** 
- **Spring Data JPA**
- **Tomcat**
- **Spring Boot Starter Web**
- **Spring Boot Starter Validation**
- **Spring Boot Starter Test**
- **MySQL 8+** 
- **MySQL Workbench 8.0.46+**
- **Maven 3.6+**
- **Visual Studio Code**
- **WSL2 2.4.12.0+**
- **Ubuntu 22.04.5+**
- **Spring Boot Maven Plugin**
- **MySQL Connect J**

## Database

7 tables: Location, Restaurant, WantToTry, HaveTried, Rating, Experience, Dish. Each table has 50 rows of data.

## Architecture

```
Web Client
        ↓
REST Controllers
        ↓
Service Layer
        ↓
Business Manager
        ↓
JPA Repositories
        ↓
restaurant_manager MySQL Database
```
The web client is an HTML file that uses Javascript's Fetch API.

## How to Run

### Prerequisites
- Java 17+, Maven 3.6+, MySQL 8+, MySQL Workbench 8+, WSL2 2.4.12.0+, Ubuntu 22.04.5+, Visual Studio Code, and a web browser.

### Database Setup
**1. In your Visual Studio Code Ubuntu terminal, run:**
```bash
sudo service mysql start
```
**2. Update `src/main/resources/application.properties` with your MySQL Workbench connection's username and password**

**3. Create restaurant_manager schema in MySQL Workbench**
- In Schemas panel, right click and select the "Create Schema" button.
- Use "restaurant_manager" as the name of the schema

**4. Run the schema.sql and data.sql files in MySQL Workbench**
- Double click the "restaurant_manager" label in the Schemas panel of MySQL Workbench to select the database
- Head back to Visual Studio Code, locate the schema.sql file in `src/main/resources/schema.sql`. Copy and paste the contents of this file into a SQL tab in MySQL Workbench. Run this file.
- Head back to Visual Studio Code, locate the data.sql file in `src/main/resources/data.sql`. Copy and paste the contents of this file into a SQL tab in MySQL Workbench. Run this file.

### Start the Server
In the Visual Studio Ubuntu Terminal, run the following commands:
```bash
mvn -DskipTests clean package
mvn spring-boot:run
```
The API runs on **http://localhost:8080**.

## API Endpoints

- `/api/locations`
- `/api/restaurants` 
- `/api/wanttotry` 
- `/api/havetried` 
- `/api/ratings` 
- `/api/experiences` 
- `/api/dishes` 

### Host and Run the Web Client
The web client is hosted locally through using Spring Boot and Tomcat. To view the web client's HTML file, it is called `index.html` and is located in `src/main/resources/static/`.

**1. Build and start the Spring Boot server:**
   ```bash
   mvn -DskipTests clean package
   mvn spring-boot:run
   ```

**2. Open your web browser and go to the following URL**
   ```
   http://localhost:8080
   ```

**3. Click **"Fetch All"** to load all data from the API.**

The web client is hosted locally using Spring Boot and Tomcat. The web client can be found at http://localhost:8080. 

### How to Use the Web Client
- **Tabs**: There are 7 tabs called: Locations, Restaurants, Want To Try, Have Tried, Ratings, Experiences, Dishes. By selecting each tab, you can view all of its data entries. 
- **To View all Entries**: Click the "Fetch All" button at the top of the page. Addtionally, the "Show ALl" button on each page will show all of the entries in that tab. 
- **To Search by ID**: Enter an ID number into the "Search by ID" box and select the "Search" button to find a specific entry.
- **Filter by Specific Fields**: Select a value from the filter dropdown and select the "Search" button to find a specific group of entries.
- **To Create an Entry**: Fill out the "Add New" box at the top of each tab and select the "Create" button.
- **To Edit an Entry**: Select the "Edit" button on any entry and then select the "Save" button. 
- **To Delete an Entry**: Select the "Delete" button on an entry and confirm the deletion.
- **To Clear All Data**: Select "Clear Tables" at the top of the page.

### Run the Service Console Client To Test the Service Layer
Run the following commands in one of the Visual Studio Code Ubuntu terminals:
   ```bash
   mvn -DskipTests clean package
   mvn spring-boot:run
   ```
In another Visual Studio Code Ubuntu Terminal, run the following command:
```bash
mvn -q exec:java -Dexec.mainClass="com.example.restaurant.app.ServiceConsoleClient" -Dexec.classpathScope=runtime
```
This test shows CRUD operations.

## Learn More About the Tools Used In This Project

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
