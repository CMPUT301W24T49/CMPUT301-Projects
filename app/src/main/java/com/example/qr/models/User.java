package com.example.qr.models;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.Map;

/**
 * Represents a user in the system with various attributes like name, role, profile picture,
 * contact information, and homepage. This class is used for managing user data within the application.
 */
public class User {
    private String id; // Document ID in Firestore
    private String firstName;
    private String name;
    private String lastName;
    private String role; // "organizer", "attendee", or "admin"
    private String profilePicture; // URL to the image
    private String phoneNumber;
    private String email;
    private String homepage; // Optional

    /**
     * Default constructor required for Firestore data mapping.
     */
    public User() {
    }

    /**
     * Constructs a new User object with the specified attributes.
     *
     * @param id The unique identifier of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param role The role of the user (organizer, attendee, or admin).
     * @param profilePicture The URL to the user's profile picture.
     * @param phoneNumber The phone number of the user.
     * @param email The email address of the user.
     * @param homepage The URL to the user's homepage.
     */
    public User(String id, String firstName, String lastName, String role, String profilePicture, String phoneNumber, String email, String homepage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.role = role;
        this.profilePicture = profilePicture;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
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
     * Gets the phone number of the user.
     *
     * @return The user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The new phone number for the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The new email address for the user.
     */
    public void setEmail(String email) {
        this.email = email;
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
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }


    /**
     * Adds a new user to the Firebase Firestore database.
     */
    public void addUserToFirebase() throws Exception {
        // Add the user to the Firestore database
        FirebaseUtil.addUser(this, documentReference -> {
        }, e -> {
            try {
                // get the user id
                String userId = this.getId();
                // log the error faild to add user <userId> to Firestore
                Log.e(TAG, "Failed to add user " + userId + " to Firestore", e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
