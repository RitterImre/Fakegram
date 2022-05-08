package com.mobilalk.fakegram.Model;

public class Post {
    private String postId;
    private String publisher;
    private String imageUrl;
    private String description;

    public Post() {
    }

    public Post(String postId, String publisher, String imageUrl, String description) {
        this.postId = postId;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
