import model.*;
import service.FoodDeliveryService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        FoodDeliveryService foodDeliveryService = new FoodDeliveryService();

        foodDeliveryService.addUser("John", "Doe", 20, "johndoe@yahoo.com", "0723456789");
        foodDeliveryService.addUser("Jane", "Doe", 21, "janedoe@yahoo.com", "0723456788");

        foodDeliveryService.addDriver("John", "Doe", 20, "BMW", 5);
        foodDeliveryService.addDriver("Jane", "Doe", 21, "Audi", 6);

        Menu menu1 = new Menu();
        menu1.addFood("Food 1", 10, 100, 100);
        menu1.addFood("Food 2", 20, 200, 200);
        menu1.addDrink("Drink 1", 5, 50, 50);
        menu1.addDrink("Drink 2", 10, 100, 100);

        Menu menu2 = new Menu();
        menu2.addFood("Food 3", 10, 100, 100);
        menu2.addFood("Food 4", 20, 200, 200);
        menu2.addDrink("Drink 3", 5, 50, 50);
        menu2.addDrink("Drink 4", 10, 100, 100);

        Review review1 = new Review("Review 1", 5);
        Review review2 = new Review("Review 2", 4);
        Review review3 = new Review("Review 1", 5);
        Review review4 = new Review("Review 2", 4);
        SortedSet<Review>reviews1 = new TreeSet<>();
        SortedSet<Review>reviews2 = new TreeSet<>();

        reviews1.add(review1);
        reviews1.add(review2);
        reviews2.add(review3);
        reviews2.add(review4);

        ArrayList<Item> items1 = new ArrayList<>();
        items1.add(new Food("Food 1", 10, 100, 100));
        items1.add(new Drink("Drink 1", 5, 50, 50));
        ArrayList<Item> items2 = new ArrayList<>();
        items2.add(new Food("Food 3", 10, 100, 100));
        items2.add(new Drink("Drink 3", 5, 50, 50));

        foodDeliveryService.addRestaurant("Restaurant 1", 1, 1, menu1, reviews1);
        foodDeliveryService.addRestaurant("Restaurant 2", 2, 2, menu2, reviews2);

        foodDeliveryService.addOrder(foodDeliveryService.getUsers().get(0), 1, 1, items1);
        foodDeliveryService.addOrder(foodDeliveryService.getUsers().get(1), 2, 2, items2);

        foodDeliveryService.addDelivery(foodDeliveryService.getOrders().first(), foodDeliveryService.getDrivers().first());
        foodDeliveryService.addDelivery(foodDeliveryService.getOrders().last(), foodDeliveryService.getDrivers().last());

        foodDeliveryService.printUsers();
        foodDeliveryService.printDrivers();
        foodDeliveryService.printRestaurants();
        foodDeliveryService.printOrders();
        foodDeliveryService.printDeliveries();

        foodDeliveryService.printDeliveredOrders();
        foodDeliveryService.timeDiff(1);
        foodDeliveryService.orderCalories(1);
        foodDeliveryService.orderPrice(1);
        foodDeliveryService.mostReviews();

        foodDeliveryService.removeDelivery(foodDeliveryService.getDeliveries().get(0).getOrder().getId());
        foodDeliveryService.removeOrder(foodDeliveryService.getOrders().first().getId());
        foodDeliveryService.removeRestaurant(foodDeliveryService.getRestaurants().get(0));
        foodDeliveryService.removeDriver(foodDeliveryService.getDrivers().first());
        foodDeliveryService.removeUser(foodDeliveryService.getUsers().get(0));

    }
}