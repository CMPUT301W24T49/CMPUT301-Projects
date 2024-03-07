package com.example.qr.models;

import androidx.annotation.NonNull;

import java.util.Map;

public class User {
    private final String id; // Document ID in Firestore
    private String name;
    private String role; // "organizer", "attendee", or "admin"
    private String profilePicture; // URL to the image
    private Map<String, String> contactInfo; // Could include email, phone, etc.
    private String homepage; // Optional

    // Constructor, getters, and setters
    public User(String id, String name, String role,
                String profilePicture, Map<String, String> contactInfo, String homepage) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.profilePicture = profilePicture;
        this.contactInfo = contactInfo;
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Map<String, String> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, String> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", contactInfo=" + contactInfo +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
