import java.util.*;

public class Restaurant {
    private String name;
    private Location location;
    private Menu menu;
    private SortedSet<Review> reviews;

    public Restaurant(String name, Location location, Menu menu, SortedSet<Review> reviews) {
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(SortedSet<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public void printReviews() {
        for (Review review : reviews) {
            System.out.println(review.getRating() + '\n' + review.getComment());
        }
    }

    public void printRestaurant() {
        System.out.println("Restaurant:");
        System.out.println(name);
        System.out.println(location.getLatitude() + " " + location.getLongitude());
        menu.printMenu();
        printReviews();
    }
}
