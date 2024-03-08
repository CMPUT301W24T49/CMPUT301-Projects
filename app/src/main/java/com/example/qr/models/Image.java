package com.example.qr.models;

import androidx.annotation.NonNull;
import java.util.Date;

/**
 * Represents an image stored in Firestore. Contains information about the image URL,
 * the user who uploaded the image, the upload time, and a reference to the related entity,
 * which could be an event or another user.
 */
public class Image {
    private final String id; // Document ID in Firestore
    private String url; // URL to the stored image
    private String uploadedBy; // Reference to User document ID
    private Date uploadTime;
    private String relatedTo; // Could reference an Event or a User document ID

    /**
     * Constructs a new Image instance.
     *
     * @param id The unique identifier of the image in Firestore.
     * @param url The URL where the image is stored.
     * @param uploadedBy The ID of the user who uploaded the image.
     * @param uploadTime The date and time when the image was uploaded.
     * @param relatedTo The ID of the entity (Event or User) the image is related to.
     */
    public Image(String id, String url, String uploadedBy, Date uploadTime, String relatedTo) {
        this.id = id;
        this.url = url;
        this.uploadedBy = uploadedBy;
        this.uploadTime = uploadTime;
        this.relatedTo = relatedTo;
    }

    /**
     * Gets the Firestore document ID of the image.
     *
     * @return The unique identifier of the image.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the URL where the image is stored.
     *
     * @return The image URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL where the image is stored.
     *
     * @param url The new image URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the ID of the user who uploaded the image.
     *
     * @return The uploader's user ID.
     */
    public String getUploadedBy() {
        return uploadedBy;
    }

    /**
     * Sets the ID of the user who uploaded the image.
     *
     * @param uploadedBy The uploader's new user ID.
     */
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    /**
     * Gets the upload time of the image.
     *
     * @return The date and time when the image was uploaded.
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * Sets the upload time of the image.
     *
     * @param uploadTime The new upload date and time.
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * Gets the ID of the entity (Event or User) the image is related to.
     *
     * @return The related entity's ID.
     */
    public String getRelatedTo() {
        return relatedTo;
    }

    /**
     * Sets the ID of the entity (Event or User) the image is related to.
     *
     * @param relatedTo The new related entity's ID.
     */
    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    @NonNull
    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", uploadTime=" + uploadTime +
                ", relatedTo='" + relatedTo + '\'' +
                '}';
    }
}
