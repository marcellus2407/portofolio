package com.moviefavorite;

public class Movie {
    public String title,image,desc;
    public double vote;
    Movie(String title, String image, double vote_avg, String desc){
        this.title=title;
        this.image=image;
        this.vote=vote_avg;
        this.desc=desc;
    }
}
