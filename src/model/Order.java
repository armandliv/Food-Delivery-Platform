package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Order implements Comparable<Order> {
    private int orderId;
    private User user;
    private Location location;
    private ArrayList<Item> items;
    private LocalDateTime time;
    public Order(int orderId, User user, Location location, ArrayList<Item> items, LocalDateTime time) {
        this.orderId = orderId;
        this.user = user;
        this.location = location;
        this.items = items;
        this.time = time;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setorderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void printOrder() {
        System.out.println("Order orderId: " + orderId);
        System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
        location.printLocation();
        System.out.println("Items: ");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Time: " + time.format(formatter));
    }

    @Override
    public int compareTo(Order p) {
        return this.orderId - p.orderId;
    }

}
