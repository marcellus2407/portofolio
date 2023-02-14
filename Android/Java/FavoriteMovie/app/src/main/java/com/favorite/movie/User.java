package com.favorite.movie;

public class User {
    public String email;
    public String username;
    public String password;
    public int id;
    public User(){
        this.id = 0;
        this.email = null;
        this.username = null;
        this.password = null;
    }
    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
