package com.example.qr;

public class Event {

    private String name;
    private String location;
    // path the poster image
    private String posterUrl;

    // Constructor
    public Event(String name, String location, String posterUrl) {
        this.name = name;
        this.location = location;
        this.posterUrl = posterUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

}
