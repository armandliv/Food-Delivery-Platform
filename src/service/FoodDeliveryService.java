package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;
import model.*;
public class FoodDeliveryService {
    private ArrayList<User> users;
    private SortedSet<Driver> drivers;
    private ArrayList<Restaurant> restaurants;
    private SortedSet<Order>orders;
    private HashMap<Integer,Boolean>delivered;
    private ArrayList<Delivery>deliveries;
    int orderIndex = 0;

    public FoodDeliveryService() {
        users = new ArrayList<>();
        drivers = new TreeSet<>();
        restaurants = new ArrayList<>();
        orders = new TreeSet<>();
        delivered = new HashMap<>();
        deliveries = new ArrayList<>();
    }

    public void addUser(String firstName, String lastName, int age, String email, String phoneNumber) {
        User user = new User(firstName, lastName, age, email, phoneNumber);
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void printUsers() {
        for (User user : users) {
            user.printUser();
        }
    }

    public void addDriver(String firstName, String lastName, int age, String car, int yearsOfExperience) {
        Driver driver = new Driver(firstName, lastName, age, car, yearsOfExperience);
        drivers.add(driver);
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
    }

    public SortedSet<Driver> getDrivers() {
        return drivers;
    }

    public void printDrivers() {
        for (Driver driver : drivers) {
            driver.printDriver();
        }
    }

    public void addRestaurant(String name,int latitude,int longitude, Menu menu, SortedSet<Review> reviews) {
        Location location = new Location(latitude, longitude);
        Restaurant restaurant = new Restaurant(name, location, menu, reviews);
        restaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }

    public void printRestaurants() {
        for (Restaurant restaurant : restaurants) {
            restaurant.printRestaurant();
        }
    }

    public void addOrder(User user,int latitude,int longitude, ArrayList<Item> items) {
        orderIndex++;
        Location location = new Location(latitude, longitude);
        Order order = new Order(orderIndex, user, location, items, LocalDateTime.now());
        orders.add(order);
    }

    public void removeOrder(int id) {
        for(Order x: orders) {
            if(x.getId() == id) {
                orders.remove(x);
                break;
            }
        }
    }

    public SortedSet<Order> getOrders() {
        return orders;
    }

    public void printOrders() {
        for(Order x: orders) {
            x.printOrder();
        }
    }

    public void addDelivery(Order order, Driver driver) {
        Delivery delivery = new Delivery(order, driver, LocalDateTime.now());
        delivered.put(order.getId(), true);
        deliveries.add(delivery);
    }

    public void removeDelivery(int id) {
        for(Delivery x: deliveries) {
            if(x.getOrder().getId() == id) {
                delivered.put(x.getOrder().getId(), false);
                deliveries.remove(x);
                break;
            }
        }
    }

    public void printDeliveries(){
        for(Delivery x: deliveries){
            x.printDelivery();
        }
    }

    public void orderPrice(int id) {
        for(Order x: orders) {
            if(x.getId() == id) {
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
        if(delivered.get(id) == null) {
            System.out.println("model.Order not delivered yet");
        }
        else {
            for (Delivery x : deliveries) {
                if (x.getOrder().getId() == id) {
                    System.out.println("Time difference: " + Duration.between(x.getOrder().getTime(), x.getTime()));
                    break;
                }
            }
        }
    }

    public void orderCalories(int id) {
        for(Order x: orders) {
            if(x.getId() == id) {
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
        for(Order x: orders) {
            if(delivered.get(x.getId())) {
                x.printOrder();
            }
        }
    }

    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

}
