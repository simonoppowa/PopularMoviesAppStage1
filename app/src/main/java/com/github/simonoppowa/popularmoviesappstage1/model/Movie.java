package com.github.simonoppowa.popularmoviesappstage1.model;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Simon on 14.03.2018.
 */

public class Movie implements Serializable{

    private int id;
    private String title;
    private String originalTitle;
    private String overview;
    private String imagePath;
    private String userRating;
    private Date releaseDate;


    public Movie(int id, String title, String originalTitle, String overview, String poster_path, String userRating, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.imagePath = poster_path;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String  getUserRating() {
        return userRating;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setUserRating(String  userRating) {
        this.userRating = userRating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseYearString() {
        SimpleDateFormat simpleDateRelease = new SimpleDateFormat("yyyy");

        String yearString = simpleDateRelease.format(releaseDate);
        Log.d("MyActivity", yearString);
        return yearString;
    }
}
