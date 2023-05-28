package model;

public abstract class Comment {
    int commentId;
    protected String comentariu;

    protected Comment(int commentId,String comentariu) {
        this.commentId = commentId;
        this.comentariu = comentariu;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comentariu;
    }

    public void setComment(String comentariu) {
        this.comentariu = comentariu;
    }
}
