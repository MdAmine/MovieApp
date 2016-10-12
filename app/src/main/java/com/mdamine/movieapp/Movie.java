package com.mdamine.movieapp;

import java.util.Date;

/**
 * Created by SAOUD Mohamed Amine on 11/10/2016.
 */

public class Movie {

    private Long id;
    private String title;
    private Date releaseDate;
    private String vote_average;
    private String poster_path;

    public Movie() {
    }

    public Movie(Long id, String title, Date releaseDate, String vote_average, String poster_path) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
