import java.util.*;
public class Order implements Comparable<Order> {
    private int id;
    private User user;
    private Location location;
    private ArrayList<Item> items;
    private TimeStamp time;
    public Order(int id, User user, Location location, ArrayList<Item> items, TimeStamp time) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.items = items;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public TimeStamp getTime() {
        return time;
    }

    public void setTime(TimeStamp time) {
        this.time = time;
    }

    public void printOrder() {
        System.out.println("Order ID: " + id);
        System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
        location.printLocation();
        System.out.println("Items: ");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
        time.printTime();
    }

    @Override
    public int compareTo(Order p) {
        return this.id - p.id;
    }

}