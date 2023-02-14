package com.cinemacgp.utils;

public class UtilsMovieAPI {
    static final public String API_KEY = "1805007963a4760044640bde94121491";
    static final public String BASE_URL = "https://api.themoviedb.org/3/";
    static final public String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    static public String getSearchMoviesURL(String keyword){
        return BASE_URL+"search/movie?api_key="+API_KEY+"&query="+keyword;
    }
    static public String getMoviesURL(int page){
        return BASE_URL+"movie/popular?api_key="+API_KEY+"&page="+page;
    }
    static public String getMovie(int id){
        return BASE_URL+"movie/"+id+"?api_key="+API_KEY;
    }
}
