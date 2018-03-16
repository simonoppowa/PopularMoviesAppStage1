package com.github.simonoppowa.popularmoviesappstage1.model;

/**
 * Created by Simon on 14.03.2018.
 */

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String imagePath;


    public Movie(int id, String title, String overview, String poster_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.imagePath = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setImage(String poster_path) {
        this.imagePath = poster_path;
    }
}
