package com.github.simonoppowa.popularmoviesappstage1.model;

public class Video {

    private String key;
    private String site;
    private String name;
    private String type;

    public Video(String key, String site, String name, String type) {
        this.key = key;
        this.site = site;
        this.name = name;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
