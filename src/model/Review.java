package model;

public class Review extends Comment implements Comparable<Review> {
    int reviewId;
    private int rating;

    public Review(int reviewId, int commentId,String comment, int rating) {
        super(commentId,comment);
        this.rating = rating;
        this.reviewId = reviewId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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
