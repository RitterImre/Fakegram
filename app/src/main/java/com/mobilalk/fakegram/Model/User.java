package com.mobilalk.fakegram.Model;

public class User {
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String bio;
    private String profileImage;

    public User() {
    }

    public User(String id, String username, String fullname, String email, String bio, String profileImage) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
