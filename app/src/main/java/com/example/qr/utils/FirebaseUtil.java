package com.example.qr.utils;

import android.graphics.drawable.Drawable;
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

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Fetches documents from a specified collection and converts them into objects of a specified class.
     *
     * @param collectionName The name of the collection to fetch.
     * @param clazz The class of the objects to be created from the documents.
     * @param listener Listener to handle the fetched objects or an error.
     * @param <T> The type parameter for the class of the objects to fetch.
     */
    public static <T> void fetchCollection(String collectionName, Class<T> clazz, OnCollectionFetchedListener<T> listener) {
        db.collection(collectionName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<T> objectList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    T object = document.toObject(clazz);
                    if (object instanceof HasId) {
                        ((HasId) object).setId(document.getId());
                    }
                    objectList.add(object);
                }
                listener.onCollectionFetched(objectList);
            } else {
                listener.onError(task.getException());
            }
        });
    }


    public interface OnCollectionFetchedListener<T> {
        void onCollectionFetched(List<T> objectList);
        void onError(Exception e);
    }

    /**
     * Interface to be implemented by classes that should handle Firestore document IDs.
     */
    public interface HasId {
        void setId(String id);
    }

}
