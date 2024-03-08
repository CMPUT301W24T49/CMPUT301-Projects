package com.example.qr.utils;

import android.net.Uri;

import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.Notification;
import com.example.qr.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirebaseUtil {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();


    /**
     * Adds a new event to the Firestore database.
     *
     * @param event The Event object to add.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void addEvent(Event event, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Events").add(event)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user The User object to add.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void addUser(User user, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Users").add(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Adds a new check-in record to the Firestore database.
     *
     * @param checkIn The CheckIn object to add.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void addCheckIn(CheckIn checkIn, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Check-Ins").add(checkIn)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Sends a notification related to an event through Firestore.
     *
     * @param notification The Notification object to send.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void sendNotification(Notification notification, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Notifications").add(notification)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Uploads an image to Firebase Storage and retrieves its download URL.
     *
     * @param imageUri The Uri of the image to upload.
     * @param path The storage path where the image will be saved.
     * @param onSuccessListener Callback for successful upload, returns the image download URL.
     * @param onFailureListener Callback for upload failure.
     */
    public static void uploadImageAndGetUrl(Uri imageUri, String path, OnSuccessListener<Uri> onSuccessListener, OnFailureListener onFailureListener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(path);

        imageRef.putFile(imageUri).continueWithTask(task -> {
            if (!task.isSuccessful() && task.getException() != null) {
                throw task.getException();
            }
            // Continue with the task to get the download URL
            return imageRef.getDownloadUrl();
        }).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }


    // You can add more methods here for updating and deleting documents, querying collections, etc.
}
