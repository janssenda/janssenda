/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.dto;

/**
 *
 * @author danimaetrix
 */
public class Title {

    private String titlename;
    private String releaseDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private String userRating;
    private String userNotes;
    private String duration;

    public Title() {
        String defaultInfo = "N/A";         // We initialize the object in case user doesn't want to enter all fields
        this.titlename = defaultInfo;
        this.releaseDate = defaultInfo;
        this.mpaaRating = defaultInfo;
        this.director = defaultInfo;
        this.studio = defaultInfo;
        this.userRating = defaultInfo;
        this.userNotes = defaultInfo;
        this.duration = defaultInfo;
    }

    public Title(String titlename) {
        String defaultInfo = "N/A";
        this.titlename = titlename;
        this.releaseDate = defaultInfo;
        this.mpaaRating = defaultInfo;
        this.director = defaultInfo;
        this.studio = defaultInfo;
        this.userRating = defaultInfo;
        this.userNotes = defaultInfo;
        this.duration = defaultInfo;        
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return titlename;
    }

    public void setTitle(String title) {
        this.titlename = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

}
