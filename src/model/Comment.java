package model;

public abstract class Comment {
    protected String comentariu;

    protected Comment(String comentariu) {
        this.comentariu = comentariu;
    }

    public String getComment() {
        return comentariu;
    }

    public void setComment(String comentariu) {
        this.comentariu = comentariu;
    }
}
