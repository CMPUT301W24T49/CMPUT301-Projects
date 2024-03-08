package com.example.qr.models;

import androidx.annotation.NonNull;
import java.util.Map;

/**
 * Represents a user in the system with various attributes like name, role, profile picture,
 * contact information, and homepage. This class is used for managing user data within the application.
 */
public class User {
    private String id; // Document ID in Firestore
    private String name;
    private String role; // "organizer", "attendee", or "admin"
    private String profilePicture; // URL to the image
    private Map<String, String> contactInfo; // Could include email, phone, etc.
    private String homepage; // Optional

    /**
     * Default constructor required for Firestore data mapping.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param id The unique identifier of the user.
     * @param name The name of the user.
     * @param role The role of the user (e.g., "organizer", "attendee", "admin").
     * @param profilePicture The URL to the user's profile picture.
     * @param contactInfo A map containing the user's contact information (e.g., email, phone).
     * @param homepage The URL to the user's homepage, if any.
     */
    public User(String id, String name, String role, String profilePicture, Map<String, String> contactInfo, String homepage) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.profilePicture = profilePicture;
        this.contactInfo = contactInfo;
        this.homepage = homepage;
    }

    // Getters and setters for each field with JavaDoc

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The new ID for the user.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the role of the user.
     *
     * @return The user's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The new role for the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the URL to the user's profile picture.
     *
     * @return The profile picture URL.
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets the URL to the user's profile picture.
     *
     * @param profilePicture The new profile picture URL for the user.
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Gets the user's contact information.
     *
     * @return A map of the user's contact information.
     */
    public Map<String, String> getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the user's contact information.
     *
     * @param contactInfo A new map of the user's contact information.
     */
    public void setContactInfo(Map<String, String> contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Gets the URL to the user's homepage.
     *
     * @return The homepage URL, if any.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Sets the URL to the user's homepage.
     *
     * @param homepage The new homepage URL for the user.
     */
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
