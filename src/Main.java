import model.*;
import service.FoodDeliveryService;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static util.Audit.initAudit;
import static util.Database.createTables;
import static util.Database.dropTables;
public class Main {
    public static void main(String[] args) throws SQLException, IOException{
        FoodDeliveryService foodDeliveryService = new FoodDeliveryService();

        initAudit();
        dropTables();
        createTables();

        foodDeliveryService.addUser(1,1,"John", "Doe", 20, "johndoe@yahoo.com", "0723456789");
        foodDeliveryService.addUser(2,2,"Jane", "Doe", 21, "janedoe@yahoo.com", "0723456788");

        foodDeliveryService.addDriver(1,3,"John", "Doe", 20, "BMW", 5);
        foodDeliveryService.addDriver(2,4,"Jane", "Doe", 21, "Audi", 6);

        Menu menu1 = new Menu(1);
        menu1.addFood(1,1,"Food 1", 10, 100, 100);
        menu1.addFood(2,2,"Food 2", 20, 200, 200);
        menu1.addDrink(1,3,"Drink 1", 5, 50, 50);
        menu1.addDrink(2,4,"Drink 2", 10, 100, 100);

        Menu menu2 = new Menu(2);
        menu2.addFood(3,5,"Food 3", 10, 100, 100);
        menu2.addFood(4,6,"Food 4", 20, 200, 200);
        menu2.addDrink(3,7,"Drink 3", 5, 50, 50);
        menu2.addDrink(4,8,"Drink 4", 10, 100, 100);

        Review review1 = new Review(1,1,"Review 1", 5);
        Review review2 = new Review(2,2,"Review 2", 4);
        Review review3 = new Review(3,3,"Review 1", 5);
        Review review4 = new Review(4,4,"Review 2", 4);
        SortedSet<Review>reviews1 = new TreeSet<>();
        SortedSet<Review>reviews2 = new TreeSet<>();

        reviews1.add(review1);
        reviews1.add(review2);
        reviews2.add(review3);
        reviews2.add(review4);

        ArrayList<Item> items1 = new ArrayList<>();
        Food mancarica = new Food(5,9,"Food 1", 10, 100, 100);
        Drink bauturica = new Drink(5,10,"Drink 1", 5, 50, 50);
        foodDeliveryService.addFood(mancarica);
        foodDeliveryService.addDrink(bauturica);
        items1.add(mancarica);
        items1.add(bauturica);
        ArrayList<Item> items2 = new ArrayList<>();
        mancarica = new Food(6,11,"Food 3", 10, 100, 100);
        bauturica = new Drink(6,12,"Drink 3", 5, 50, 50);
        foodDeliveryService.addFood(mancarica);
        foodDeliveryService.addDrink(bauturica);
        items2.add(mancarica);
        items2.add(bauturica);

        foodDeliveryService.addRestaurant(1,1,"Restaurant 1", 1, 1, menu1, reviews1);
        foodDeliveryService.addRestaurant(2,2,"Restaurant 2", 2, 2, menu2, reviews2);

        foodDeliveryService.addOrder(foodDeliveryService.getUsers().get(0), 3,1, 1, items1);
        foodDeliveryService.addOrder(foodDeliveryService.getUsers().get(1), 4,2, 2, items2);

        foodDeliveryService.addDelivery(1,foodDeliveryService.getOrders().first(), foodDeliveryService.getDrivers().first());
        foodDeliveryService.addDelivery(2,foodDeliveryService.getOrders().last(), foodDeliveryService.getDrivers().last());

        foodDeliveryService.getUsers();
        foodDeliveryService.getDrivers();
        foodDeliveryService.getRestaurants();
        foodDeliveryService.getOrders();
        foodDeliveryService.getDeliveries();

        Location InsuleleSadwichDeSud = new Location(5,3,3);
        foodDeliveryService.addLocation(InsuleleSadwichDeSud);
        foodDeliveryService.updateUserPhoneNumber(foodDeliveryService.getUsers().get(0), "0723456780");
        foodDeliveryService.updateYearsOfExperience(foodDeliveryService.getDrivers().first(), 10);
        foodDeliveryService.updateRestaurantName(foodDeliveryService.getRestaurants().get(0), "Restaurant 3");
        foodDeliveryService.updateOrderLocation(foodDeliveryService.getOrders().first(), InsuleleSadwichDeSud);
        foodDeliveryService.updateOrderTime(foodDeliveryService.getOrders().first(), LocalDateTime.now());
        foodDeliveryService.updateDeliveryTime(foodDeliveryService.getDeliveries().get(0), LocalDateTime.now());

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

        foodDeliveryService.removeDelivery(foodDeliveryService.getDeliveries().get(0).getOrder().getOrderId());
        foodDeliveryService.removeOrder(foodDeliveryService.getOrders().first().getOrderId());
        foodDeliveryService.removeRestaurant(foodDeliveryService.getRestaurants().get(0));
        foodDeliveryService.removeDriver(foodDeliveryService.getDrivers().first());
        foodDeliveryService.removeUser(foodDeliveryService.getUsers().get(0));

        dropTables();

    }
}