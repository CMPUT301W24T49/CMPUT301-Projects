package com.example.qr.models;

import androidx.annotation.NonNull;

import com.google.type.Date;

public class Image {
    private final String id; // Document ID in Firestore
    private String url; // URL to the stored image
    private String uploadedBy; // Reference to User document ID
    private Date uploadTime;
    private String relatedTo; // Could reference an Event or a User document ID

    // Constructor, getters, and setters
    public Image(String id, String url, String uploadedBy, Date uploadTime, String relatedTo) {
        this.id = id;
        this.url = url;
        this.uploadedBy = uploadedBy;
        this.uploadTime = uploadTime;
        this.relatedTo = relatedTo;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

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
