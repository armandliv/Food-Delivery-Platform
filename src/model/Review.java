package model;

public class Review extends Comment implements Comparable<Review> {
    private int rating;

    public Review(String comment, int rating) {
        super(comment);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(Review p) {
        return -this.rating + p.rating;
    }
}
