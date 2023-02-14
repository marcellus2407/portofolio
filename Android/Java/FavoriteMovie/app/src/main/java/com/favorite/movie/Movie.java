package com.favorite.movie;

public class Movie {
    String title,image,desc;
    double vote;
    Movie(String title,String image,double vote_avg,String desc){
        this.title=title;
        this.image=image;
        this.vote=vote_avg;
        this.desc=desc;
    }
}
