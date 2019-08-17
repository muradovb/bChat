package com.bmmuradov.core;

public class User {

    //properties
    private String username;
    private String password;
    private String userId;

    //TODO: define arraylist of model to keep user messages
    //constructors
    public User() {

    }

    public User(String username, String password, String uId) {
        this.username=username;
        this.password=password;
        this.userId=uId;
    }
    //methods
    public String getName() {
        return username;
    }
    public String getPhone() {
        return username;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUsername(String newUsername) {
        this.username=newUsername;
    }

}
