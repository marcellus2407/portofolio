package com.cinemacgp.model;

public class Movie {
    public int id;
    public String title,image,desc;
    public double vote;

    public Movie(int id, String title, String image,double vote, String desc) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.vote = vote;
    }
}
