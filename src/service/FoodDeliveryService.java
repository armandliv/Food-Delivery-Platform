package service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;
import model.*;
import database.DatabaseConnection;
import java.sql.SQLException;

import static util.Audit.printAction;

public class FoodDeliveryService {
    private DatabaseConnection dbconn;
    private ArrayList<User> users;
    private SortedSet<Driver> drivers;
    private ArrayList<Restaurant> restaurants;
    private SortedSet<Order>orders;
    private HashMap<Integer,Boolean>delivered;
    private ArrayList<Delivery>deliveries;
    int orderIndex = 0;

    public FoodDeliveryService() throws SQLException{
        users = new ArrayList<>();
        drivers = new TreeSet<>();
        restaurants = new ArrayList<>();
        orders = new TreeSet<>();
        delivered = new HashMap<>();
        deliveries = new ArrayList<>();
        dbconn = DatabaseConnection.getInstance();
    }

    public void addUser(int userId, int personId,String firstName, String lastName, int age, String email, String phoneNumber) throws SQLException{
        printAction("addUser");
        User user = new User(userId,personId,firstName, lastName, age, email, phoneNumber);
        users.add(user);
        dbconn.insertUser(user);
    }

    public void removeUser(User user) throws SQLException{
        printAction("removeUser");
        users.remove(user);
        dbconn.deleteUser(user.getUserId());
    }

    public void updateUserPhoneNumber(User user, String phoneNumber) throws SQLException{
        printAction("updateUserPhoneNumber");
        user.setPhoneNumber(phoneNumber);
        dbconn.updateUserPhoneNumber(user.getUserId(), phoneNumber);
    }

    public ArrayList<User> getUsers() throws SQLException{
        printAction("getUsers");
        users = dbconn.getUsers();
        return users;
    }

    public void printUsers() {
        printAction("printUsers");
        for (User user : users) {
            user.printUser();
        }
    }

    public void addDriver(int driverId, int personId,String firstName, String lastName, int age, String car, int yearsOfExperience) throws SQLException{
        printAction("addDriver");
        Driver driver = new Driver(driverId, personId,firstName, lastName, age, car, yearsOfExperience);
        drivers.add(driver);
        dbconn.insertDriver(driver);
    }

    public void removeDriver(Driver driver) throws SQLException{
        printAction("removeDriver");
        drivers.remove(driver);
        dbconn.deleteDriver(driver.getDriverId());
    }

    public void updateYearsOfExperience(Driver driver, int yearsOfExperience) throws SQLException{
        printAction("updateYearsOfExperience");
        driver.setYearsOfExperience(yearsOfExperience);
        dbconn.updateYearsOfExperience(driver.getDriverId(), yearsOfExperience);
    }

    public SortedSet<Driver> getDrivers() throws SQLException {
        printAction("getDrivers");
        drivers = dbconn.getDrivers();
        return drivers;
    }

    public void printDrivers() {
        printAction("printDrivers");
        for (Driver driver : drivers) {
            driver.printDriver();
        }
    }

    public void addRestaurant(int restaurantId, int locationId, String name,int latitude,int longitude, Menu menu, SortedSet<Review> reviews) throws SQLException{
        printAction("addRestaurant");
        Location location = new Location(locationId, latitude, longitude);
        Restaurant restaurant = new Restaurant(restaurantId, name, location, menu, reviews);
        restaurants.add(restaurant);
        dbconn.addRestaurant(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) throws SQLException{
        printAction("removeRestaurant");
        restaurants.remove(restaurant);
        dbconn.deleteRestaurant(restaurant.getRestaurantId());
    }

    public void updateRestaurantName(Restaurant restaurant, String name) throws SQLException{
        printAction("updateRestaurantName");
        restaurant.setName(name);
        dbconn.updateRestaurantName(restaurant.getRestaurantId(), name);
    }

    public void printRestaurants() {
        printAction("printRestaurants");
        for (Restaurant restaurant : restaurants) {
            restaurant.printRestaurant();
        }
    }

    public void addOrder(User user,int locationId, int latitude,int longitude, ArrayList<Item> items) throws SQLException{
        printAction("addOrder");
        orderIndex++;
        Location location = new Location(locationId,latitude, longitude);
        Order order = new Order(orderIndex, user, location, items, LocalDateTime.now());
        orders.add(order);
        dbconn.addOrder(order);
    }

    public void removeOrder(int id) throws SQLException{
        printAction("removeOrder");
        for(Order x: orders) {
            if(x.getOrderId() == id) {
                orders.remove(x);
                dbconn.deleteOrder(id);
                break;
            }
        }
    }

    public void updateOrderLocation(Order order, Location location) throws SQLException{
        printAction("updateOrderLocation");
        order.setLocation(location);
        dbconn.updateOrderLocation(order.getOrderId(), location);
    }

    public void updateOrderTime(Order order, LocalDateTime timp) throws SQLException{
        printAction("updateOrderTime");
        order.setTime(timp);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = timp.format(dateTimeFormatter);
        dbconn.updateOrderTime(order.getOrderId(), time);
    }

    public SortedSet<Order> getOrders() throws SQLException{
        printAction("getOrders");
        orders = dbconn.getOrders();
        return orders;
    }

    public void printOrders() {
        printAction("printOrders");
        for(Order x: orders) {
            x.printOrder();
        }
    }

    public void addDelivery(int deliveryId,Order order, Driver driver) throws SQLException{
        printAction("addDelivery");
        Delivery delivery = new Delivery(deliveryId,order, driver, LocalDateTime.now());
        delivered.put(order.getOrderId(), true);
        deliveries.add(delivery);
        dbconn.addDelivery(delivery);
    }

    public void removeDelivery(int id) throws SQLException {
        printAction("removeDelivery");
        for(Delivery x: deliveries) {
            if(x.getOrder().getOrderId() == id) {
                delivered.put(x.getOrder().getOrderId(), false);
                deliveries.remove(x);
                dbconn.deleteDelivery(id);
                break;
            }
        }
    }

    public void updateDeliveryTime(Delivery delivery, LocalDateTime timp) throws SQLException{
        printAction("updateDeliveryTime");
        delivery.setTime(timp);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = timp.format(dateTimeFormatter);
        dbconn.updateDeliveryTime(delivery.getDeliveryId(), time);
    }

    public void printDeliveries(){
        printAction("printDeliveries");
        for(Delivery x: deliveries){
            x.printDelivery();
        }
    }

    public void orderPrice(int id) {
        printAction("orderPrice");
        for(Order x: orders) {
            if(x.getOrderId() == id) {
                int s=0;
                for (Item item : x.getItems()) {
                    s += item.getPrice();
                }
                System.out.println("model.Order price: " + s);
                break;
            }
        }
    }

    public void timeDiff(int id) {
        printAction("timeDiff");
        if(delivered.get(id) == null) {
            System.out.println("model.Order not delivered yet");
        }
        else {
            for (Delivery x : deliveries) {
                if (x.getOrder().getOrderId() == id) {
                    System.out.println("Time difference: " + Duration.between(x.getOrder().getTime(), x.getTime()));
                    break;
                }
            }
        }
    }

    public void orderCalories(int id) {
        printAction("orderCalories");
        for(Order x: orders) {
            if(x.getOrderId() == id) {
                int s=0;
                for (Item item : x.getItems()) {
                    s += item.getCalories();
                }
                System.out.println("model.Order calories: " + s);
                break;
            }
        }
    }

    public void mostReviews() {
        printAction("mostReviews");
        int max = 0;
        Restaurant best = null;
        for(Restaurant x: restaurants) {
            if(x.getReviews().size() > max) {
                max = x.getReviews().size();
                best = x;
            }
        }
        if(best != null)
            System.out.println("Best restaurant: " + best.getName());
        else
            System.out.println("No restaurants");
    }

    public void printDeliveredOrders() {
        printAction("printDeliveredOrders");
        for(Order x: orders) {
            if(delivered.get(x.getOrderId())) {
                x.printOrder();
            }
        }
    }

    public ArrayList<Delivery> getDeliveries() {
        printAction("getDeliveries");
        return deliveries;
    }

    public ArrayList<Restaurant> getRestaurants() {
        printAction("getRestaurants");
        return restaurants;
    }

    public void addFood(Food food) throws SQLException{
        printAction("addFood");
        dbconn.addFood(food);
    }

    public void addDrink(Drink drink) throws SQLException{
        printAction("addDrink");
        dbconn.addDrink(drink);
    }

    public void addLocation(Location location) throws SQLException{
        printAction("addLocation");
        dbconn.addLocation(location);
    }


}
