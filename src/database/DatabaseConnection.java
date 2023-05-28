package database;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import model.*;
import model.Driver;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private final Connection connection;

    private DatabaseConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/proiectpao?allowMultiQueries=true";
        String username = "root";
        String password = "armand";
        connection = DriverManager.getConnection(url, username, password);
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void createTables() throws SQLException, IOException {
        Path path = Path.of("dbcreation.txt");
        String query = Files.readString(path);

        Statement statement = connection.createStatement();
        statement.execute(query);
    }
    public void dropTables() throws SQLException,IOException {
        Path path = Path.of("droptables.txt");
        System.out.println(path);
        String query = Files.readString(path);

        Statement statement = connection.createStatement();
        statement.execute(query);
    }

    public void insertUser(User user) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO persons VALUES( %d, '%s', '%s', %d)", user.getPersonId(), user.getFirstName(), user.getLastName(), user.getAge());
        statement.execute(query);
        query = String.format("INSERT INTO users VALUES( %d, '%s', '%s', %d)", user.getUserId(), user.getEmail(), user.getPhoneNumber(), user.getPersonId());
        statement.execute(query);
    }

    public void deleteUser(int userId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("DELETE FROM users WHERE userId = %d", userId);
        statement.execute(query);
        //query = String.format("DELETE FROM persons WHERE personId = %d", personId);
        //statement.execute(query);
    }

    public ArrayList<User> getUsers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<User> users = new ArrayList<>();
        while (!resultSet.isClosed() && resultSet.next()) {
            int userId = resultSet.getInt("userId");
            String email = resultSet.getString("email");
            String phoneNumber = resultSet.getString("phoneNumber");
            int personId = resultSet.getInt("personId");
            /*query = String.format("SELECT * FROM persons WHERE personId = %d", personId);
            ResultSet resultSet1 = statement.executeQuery(query);
            resultSet1.next();
            String firstName = resultSet1.getString("firstName");
            String lastName = resultSet1.getString("lastName");
            int age = resultSet1.getInt("age");*/
            String firstName = getPersonFirstName(personId);
            String lastName = getPersonLastName(personId);
            int age = getPersonAge(personId);
            User user = new User(userId, personId, firstName, lastName, age, email, phoneNumber);
            users.add(user);
        }
        return users;
    }

    public void updateUserPhoneNumber(int userId, String phoneNumber) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE users SET phoneNumber = '%s' WHERE userId = %d", phoneNumber, userId);
        statement.execute(query);
    }

    public void insertDriver(model.Driver driver) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO persons VALUES( %d, '%s', '%s', %d)", driver.getPersonId(), driver.getFirstName(), driver.getLastName(), driver.getAge());
        statement.execute(query);
        query = String.format("INSERT INTO drivers VALUES( %d, '%s', %d, %d)", driver.getDriverId(), driver.getCar(), driver.getYearsOfExperience(), driver.getPersonId());
        statement.execute(query);
    }

    public void deleteDriver(int driverId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("DELETE FROM drivers WHERE driverId = %d", driverId);
        statement.execute(query);
        //query = String.format("DELETE FROM persons WHERE personId = %d", personId);
        //statement.execute(query);
    }

    public void updateYearsOfExperience(int driverId, int yearsOfExperience) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE drivers SET yearsOfExperience = %d WHERE driverId = %d", yearsOfExperience, driverId);
        statement.execute(query);
    }

    public SortedSet<Driver> getDrivers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM drivers";
        ResultSet resultSet = statement.executeQuery(query);
        SortedSet<Driver> drivers = new TreeSet<>();
        while (!resultSet.isClosed() && resultSet.next()) {
            int driverId = resultSet.getInt("driverId");
            String car = resultSet.getString("car");
            int yearsOfExperience = resultSet.getInt("yearsOfExperience");
            int personId = resultSet.getInt("personId");
            query = String.format("SELECT * FROM persons WHERE personId = %d", personId);
            ResultSet resultSet1 = statement.executeQuery(query);
            resultSet1.next();
            String firstName = resultSet1.getString("firstName");
            String lastName = resultSet1.getString("lastName");
            int age = resultSet1.getInt("age");
            Driver driver = new Driver(driverId, personId, firstName, lastName, age, car, yearsOfExperience);
            drivers.add(driver);
        }
        return drivers;
    }

    public void addRestaurant(Restaurant restaurant) throws SQLException {
        Statement statement = connection.createStatement();
        addLocation(restaurant.getLocation());
        addMenu(restaurant.getMenu());
        addRestaurantReviews(restaurant.getRestaurantId(),restaurant.getReviews());
        String query = String.format("INSERT INTO restaurants VALUES( %d, '%s', %d, %d)", restaurant.getRestaurantId(), restaurant.getName(), restaurant.getLocation().getLocationId(), restaurant.getMenu().getMenuId());
        statement.execute(query);
    }

    public void addRestaurantReviews(int restaurantId, Set<Review> reviews) throws SQLException {
        Statement statement = connection.createStatement();
        for (Review review : reviews) {
            addReview(review);
            String query = String.format("INSERT INTO restaurantReviews VALUES( %d, %d)", restaurantId, review.getReviewId());
            statement.execute(query);
        }
    }

    public void addReview(Review review) throws SQLException {
        Statement statement = connection.createStatement();
        addComment(review.getCommentId(), review.getComment());
        String query = String.format("INSERT INTO reviews VALUES( %d, '%s', %d)", review.getReviewId(), review.getRating(), review.getCommentId());
        statement.execute(query);
    }

    public void addComment(int commentId,String comment) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO comments VALUES( %d, '%s')", commentId, comment);
        statement.execute(query);
    }

    public void addLocation(Location location) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO locations VALUES( %d, %d, %d)", location.getLocationId(), location.getLatitude(), location.getLongitude());
        statement.execute(query);
    }

    public void addMenu(Menu menu) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO menus VALUES( %d)", menu.getMenuId());
        addMenuFoods(menu.getMenuId(),menu.getFoods());
        addMenuDrinks(menu.getMenuId(),menu.getDrinks());
        statement.execute(query);
    }

    public void addMenuFoods(int menuId, ArrayList<Food> foods) throws SQLException {
        Statement statement = connection.createStatement();
        for (Food food : foods) {
            addFood(food);
            String query = String.format("INSERT INTO menuFoods VALUES( %d, %d)", menuId, food.getFoodId());
            statement.execute(query);
        }
    }

    public void addMenuDrinks(int menuId, ArrayList<Drink> drinks) throws SQLException {
        Statement statement = connection.createStatement();
        for (Drink drink : drinks) {
            addDrink(drink);
            String query = String.format("INSERT INTO menuDrinks VALUES( %d, %d)", menuId, drink.getDrinkId());
            statement.execute(query);
        }
    }

    public void addFood(Food food) throws SQLException {
        Statement statement = connection.createStatement();
        addItem(food);
        String query = String.format("INSERT INTO foods VALUES( %d, '%s', %d)", food.getFoodId(), food.getWeight(), food.getItemId());
        statement.execute(query);
    }

    public void addDrink(Drink drink) throws SQLException {
        Statement statement = connection.createStatement();
        addItem(drink);
        String query = String.format("INSERT INTO drinks VALUES( %d, '%s', %d)", drink.getDrinkId(), drink.getVolume(), drink.getItemId());
        statement.execute(query);
    }

    public void addItem(Item item) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO items VALUES( %d, '%s',%d,%d)", item.getItemId(), item.getName(), item.getPrice(), item.getCalories());
        statement.execute(query);
    }

    public void deleteRestaurant(int restaurantId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("DELETE FROM restaurants WHERE restaurantId = %d", restaurantId);
        statement.execute(query);
    }

    public void updateRestaurantName(int restaurantId, String name) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE restaurants SET name = '%s' WHERE restaurantId = %d", name, restaurantId);
        statement.execute(query);
    }


    public ArrayList<Restaurant> getRestaurants() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM restaurants";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            int restaurantId = resultSet.getInt("restaurantId");
            String name = resultSet.getString("name");
            int locationId = resultSet.getInt("locationId");
            int menuId = resultSet.getInt("menuId");
            Location location = getLocation(locationId);
            Menu menu = getMenu(menuId);
            SortedSet<Review> reviews = getRestaurantReviews(restaurantId);
            Restaurant restaurant = new Restaurant(restaurantId, name, location, menu, reviews);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public SortedSet<Review> getRestaurantReviews(int restaurantId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM restaurantReviews WHERE restaurantId = %d", restaurantId);
        ResultSet resultSet = statement.executeQuery(query);
        SortedSet<Review> reviews = new TreeSet<>();
        while (resultSet.next()) {
            int reviewId = resultSet.getInt("reviewId");
            Review review = getReview(reviewId);
            reviews.add(review);
        }
        return reviews;
    }

    public Review getReview(int reviewId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM reviews WHERE reviewId = %d", reviewId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int rating = resultSet.getInt("rating");
        int commentId = resultSet.getInt("commentId");
        return new Review(reviewId, commentId, getCommentText(commentId),rating);
    }

    public String getCommentText(int commentId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM comments WHERE commentId = %d", commentId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString("comment");
    }

    public Location getLocation(int locationId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM locations WHERE locationId = %d", locationId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int latitude = resultSet.getInt("latitude");
        int longitude = resultSet.getInt("longitude");
        return new Location(locationId, latitude, longitude);
    }

    public Menu getMenu(int menuId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM menus WHERE menuId = %d", menuId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        ArrayList<Food> foods = getMenuFoods(menuId);
        ArrayList<Drink> drinks = getMenuDrinks(menuId);
        return new Menu(menuId, foods, drinks);
    }

    public ArrayList<Food> getMenuFoods(int menuId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM menuFoods WHERE menuId = %d", menuId);
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            int foodId = resultSet.getInt("foodId");
            foods.add(getFood(foodId));
        }
        return foods;
    }

    public Food getFood(int foodId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM foods WHERE foodId = %d", foodId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int weight = resultSet.getInt("weight");
        int itemId = resultSet.getInt("itemId");
        return new Food(foodId, itemId, getItemName(itemId), getItemPrice(itemId), getItemCalories(itemId), weight);
    }

    public ArrayList<Drink> getMenuDrinks(int menuId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM menuDrinks WHERE menuId = %d", menuId);
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Drink> drinks = new ArrayList<>();
        while (resultSet.next()) {
            int drinkId = resultSet.getInt("drinkId");
            drinks.add(getDrink(drinkId));
        }
        return drinks;
    }

    public Drink getDrink(int drinkId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM drinks WHERE drinkId = %d", drinkId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int volume = resultSet.getInt("volume");
        int itemId = resultSet.getInt("itemId");
        return new Drink(drinkId, itemId, getItemName(itemId), getItemPrice(itemId), getItemCalories(itemId), volume);
    }

    public String getItemName(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM items WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString("name");
    }

    public int getItemPrice(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM items WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt("price");
    }

    public int getItemCalories(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM items WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt("calories");
    }

    public void addOrder(Order order) throws SQLException {
        Statement statement = connection.createStatement();
        addLocation(order.getLocation());
        addOrderItems(order.getOrderId(), order.getItems());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = order.getTime().format(dateTimeFormatter);
        String query = String.format("INSERT INTO orders VALUES (%d, %d, %d, '%s')", order.getOrderId(), order.getUser().getUserId(), order.getLocation().getLocationId(), time);
        statement.executeUpdate(query);
    }

    public void addOrderItems(int orderId, ArrayList<Item> items) throws SQLException {
        for (Item item : items) {
            addOrderItem(orderId, item);
        }
    }

    public void addOrderItem(int orderId, Item item) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("INSERT INTO orderItems VALUES (%d, %d)", orderId, item.getItemId());
        statement.executeUpdate(query);
    }

    public void deleteOrder(int orderId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("DELETE FROM orders WHERE orderId = %d", orderId);
        statement.executeUpdate(query);
    }

    public void updateOrderLocation(int orderId, Location location) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE orders SET locationId = %d WHERE orderId = %d", location.getLocationId(), orderId);
        statement.executeUpdate(query);
    }

    public void updateOrderTime(int orderId, String time) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE orders SET time = '%s' WHERE orderId = %d", time, orderId);
        statement.executeUpdate(query);
    }

    public SortedSet<Order> getOrders() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM orders";
        ResultSet resultSet = statement.executeQuery(query);
        SortedSet<Order> orders = new TreeSet<>();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("orderId");
            int userId = resultSet.getInt("userId");
            int locationId = resultSet.getInt("locationId");
            String time = resultSet.getString("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            User user = getUser(userId);
            Location location = getLocation(locationId);
            ArrayList<Item> items = getOrderItems(orderId);
            orders.add(new Order(orderId, user, location, items, dateTime));
        }
        return orders;
    }
    /*
    CREATE TABLE users (
    userId int(12) NOT NULL,
    email varchar(100) NOT NULL,
    phoneNumber varchar(100) NOT NULL,
    personId int(12) NOT NULL
     */
    public User getUser(int userId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM users WHERE userId = %d", userId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phoneNumber");
        int personId = resultSet.getInt("personId");
        return new User(userId, personId, getPersonFirstName(personId), getPersonLastName(personId), getPersonAge(personId) , email, phoneNumber);
    }

    public String getPersonFirstName(int personId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM persons WHERE personId = %d", personId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString("firstName");
    }

    public String getPersonLastName(int personId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM persons WHERE personId = %d", personId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString("lastName");
    }

    public int getPersonAge(int personId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM persons WHERE personId = %d", personId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt("age");
    }

    public ArrayList<Item> getOrderItems(int orderId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM orderItems WHERE orderId = %d", orderId);
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            int itemId = resultSet.getInt("itemId");
            // search in Foods table and Drinks table if itemId is a food or a drink
            if (isFood(itemId)) {
                items.add(getFoodByItemId(itemId));
            } else {
                items.add(getDrinkByItemId(itemId));
            }
        }
        return items;
    }

    public boolean isFood(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM foods WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.next();
    }

    public Food getFoodByItemId(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM foods WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int foodId = resultSet.getInt("foodId");
        int weight = resultSet.getInt("weight");
        return new Food(foodId, itemId, getItemName(itemId), getItemPrice(itemId), getItemCalories(itemId), weight);
    }

    public Drink getDrinkByItemId(int itemId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM drinks WHERE itemId = %d", itemId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int drinkId = resultSet.getInt("drinkId");
        int volume = resultSet.getInt("volume");
        return new Drink(drinkId, itemId, getItemName(itemId), getItemPrice(itemId), getItemCalories(itemId), volume);
    }
    public void addDelivery(Delivery delivery) throws SQLException {
        Statement statement = connection.createStatement();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = delivery.getTime().format(dateTimeFormatter);
        String query = String.format("INSERT INTO deliveries VALUES (%d, %d, %d, '%s')", delivery.getDeliveryId(), delivery.getOrder().getOrderId(), delivery.getDriver().getDriverId(), time);
        statement.executeUpdate(query);
    }

    public void deleteDelivery(int deliveryId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("DELETE FROM deliveries WHERE deliveryId = %d", deliveryId);
        statement.executeUpdate(query);
    }

    public void updateDeliveryTime(int deliveryId, String time) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("UPDATE deliveries SET time = '%s' WHERE deliveryId = %d", time, deliveryId);
        statement.executeUpdate(query);
    }

    /*
     public SortedSet<Order> getOrders() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM orders";
        ResultSet resultSet = statement.executeQuery(query);
        SortedSet<Order> orders = new TreeSet<>();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("orderId");
            int userId = resultSet.getInt("userId");
            int locationId = resultSet.getInt("locationId");
            String time = resultSet.getString("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            User user = getUser(userId);
            Location location = getLocation(locationId);
            ArrayList<Item> items = getOrderItems(orderId);
            orders.add(new Order(orderId, user, location, items, dateTime));
        }
        return orders;
    }


     public User getUser(int userId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM users WHERE userId = %d", userId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phoneNumber");
        int personId = resultSet.getInt("personId");
        return new User(userId, personId, getPersonFirstName(personId), getPersonLastName(personId), getPersonAge(personId) , email, phoneNumber);
    }
     */
    public ArrayList<Delivery> getDeliveries() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM deliveries";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Delivery> deliveries = new ArrayList<>();
        while(resultSet.next()){
            int deliveryId = resultSet.getInt("deliveryId");
            int orderId = resultSet.getInt("orderId");
            int driverId = resultSet.getInt("driverId");
            String time = resultSet.getString("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            Order order = getOrder(orderId);
            Driver driver = getDriver(driverId);
            deliveries.add(new Delivery(deliveryId, order, driver, dateTime));
        }
        return deliveries;
    }

    public Order getOrder (int orderId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM orders WHERE orderId = %d", orderId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int userId = resultSet.getInt("userId");
        int locationId = resultSet.getInt("locationId");
        String time = resultSet.getString("time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        User user = getUser(userId);
        Location location = getLocation(locationId);
        ArrayList<Item> items = getOrderItems(orderId);
        return new Order(orderId, user, location, items, dateTime);
    }

    public Driver getDriver(int driverId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = String.format("SELECT * FROM drivers WHERE driverId = %d", driverId);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int personId = resultSet.getInt("personId");
        int yearsOfExperience = resultSet.getInt("yearsOfExperience");
        String car = resultSet.getString("car");
        return new Driver(driverId, personId, getPersonFirstName(personId), getPersonLastName(personId), getPersonAge(personId), car, yearsOfExperience);
    }
}


/*
    CREATE TABLE deliveries (
    deliveryId int(12) NOT NULL,
    orderId varchar(100) NOT NULL,
    driverId varchar(100) NOT NULL,
    time varchar(100) NOT NULL



     public class Delivery {
    int deliveryId;
    private Order order;
    private Driver driver;
    private LocalDateTime time;
     */
/*
    CREATE TABLE comments (
    commentId int(12) NOT NULL,
    comentariu varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE comments
  ADD PRIMARY KEY (commentId);

CREATE TABLE deliveries (
    deliveryId int(12) NOT NULL,
    orderId varchar(100) NOT NULL,
    driverId varchar(100) NOT NULL,
    time varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

Alter table deliveries
  ADD PRIMARY KEY (deliveryId);

CREATE TABLE drinks (
    drinkId int(12) NOT NULL,
    volume int(12) NOT NULL,
    itemId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE drinks
  ADD PRIMARY KEY (drinkId);

CREATE TABLE drivers (
    driverId int(12) NOT NULL,
    car varchar(100) NOT NULL,
    yearsOfExperience int(12) NOT NULL,
    personId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE drivers
  ADD PRIMARY KEY (driverId);

CREATE TABLE foods (
    foodId int(12) NOT NULL,
    weight int(12) NOT NULL,
    itemId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE foods
  ADD PRIMARY KEY (foodId);

CREATE TABLE items (
    itemId int(12) NOT NULL,
    name varchar(100) NOT NULL,
    price int(12) NOT NULL,
    calories int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE items
  ADD PRIMARY KEY (itemId);

CREATE TABLE locations (
    locationId int(12) NOT NULL,
    latitude int(12) NOT NULL,
    longitude int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE locations
  ADD PRIMARY KEY (locationId);

CREATE TABLE menus (
    menuId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE menus
  ADD PRIMARY KEY (menuId);

CREATE TABLE menuFoods (
    menuId int(12) NOT NULL,
    foodId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE menuFoods
  ADD PRIMARY KEY (menuId,foodId);

CREATE TABLE menuDrinks (
    menuId int(12) NOT NULL,
    drinkId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE menuDrinks
  ADD PRIMARY KEY (menuId,drinkId);

CREATE TABLE orders (
    orderId int(12) NOT NULL,
    userId int(12) NOT NULL,
    locationId int(12) NOT NULL,
    time varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE orders
  ADD PRIMARY KEY (orderId);

CREATE TABLE orderItems (
    orderId int(12) NOT NULL,
    itemId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE orderItems
  ADD PRIMARY KEY (orderId,itemId);

CREATE TABLE persons (
    personId int(12) NOT NULL,
    firstName varchar(100) NOT NULL,
    lastName varchar(100) NOT NULL,
    age int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE persons
  ADD PRIMARY KEY (personId);

CREATE TABLE restaurants (
    restaurantId int(12) NOT NULL,
    name varchar(100) NOT NULL,
    locationId int(12) NOT NULL,
    menuId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE restaurants
  ADD PRIMARY KEY (restaurantId);

CREATE TABLE restaurantReviews (
    restaurantId int(12) NOT NULL,
    reviewId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE restaurantReviews
  ADD PRIMARY KEY (restaurantId,reviewId);

CREATE TABLE reviews (
    reviewId int(12) NOT NULL,
    rating int(12) NOT NULL,
    commentId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE reviews
  ADD PRIMARY KEY (reviewId);

CREATE TABLE users (
    userId int(12) NOT NULL,
    email varchar(100) NOT NULL,
    phoneNumber varchar(100) NOT NULL,
    personId int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE users
  ADD PRIMARY KEY (userId);

COMMIT;




public class Restaurant {
    int restaurantId;
    private String name;
    private Location location;
    private Menu menu;
    private SortedSet<Review> reviews;



     */
