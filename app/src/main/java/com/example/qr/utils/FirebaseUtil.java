package com.example.qr.utils;

import static com.example.qr.activities.MainActivity.androidId;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.Notification;
import com.example.qr.models.SignUp;
import com.example.qr.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class to handle Firebase Firestore and Storage operations.
 */
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
    public static void addEvent(Event event, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Events")
                .document(event.getId())
                .set(event)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    /**
     * Deletes an event from the Firestore database.
     *
     * @param eventId The ID of the event to delete.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void deleteEvent(final String eventId,
                                   final OnSuccessListener<Void> onSuccessListener,
                                   final OnFailureListener onFailureListener) {
        final WriteBatch batch = db.batch();
        final DocumentReference eventDocRef = db.collection("Events").document(eventId);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        String[] subcollectionsPaths = {"Notifications", "Attendees"};
        for (String subcollectionPath : subcollectionsPaths) {
            tasks.add(eventDocRef.collection(subcollectionPath).get());
        }

        tasks.add(db.collection("CheckIn").whereEqualTo("eventId", eventId).get());

        tasks.add(db.collection("SignUp").whereEqualTo("eventId", eventId).get());
        Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> list) {

                for (Object obj : list) {
                    QuerySnapshot querySnapshot = (QuerySnapshot) obj;
                    for (DocumentSnapshot docSnap : querySnapshot.getDocuments()) {
                        batch.delete(docSnap.getReference());
                    }
                }

                batch.delete(eventDocRef);
                batch.commit().addOnSuccessListener(onSuccessListener)
                        .addOnFailureListener(onFailureListener);
            }
        }).addOnFailureListener(onFailureListener);

        String[] subcollectionsPath = {"Notifications",};

        // Delete each subcollection
        for (String collectionPath : subcollectionsPath) {
            deleteSubcollection(eventId, collectionPath, onSuccessListener, onFailureListener);
        }
        db.collection("Events").document(eventId)
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    private static void deleteSubcollection(String eventId, String subcollectionPath,
                                            OnSuccessListener<Void> onSuccessListener,
                                            OnFailureListener onFailureListener) {
        db.collection("Events").document(eventId).collection(subcollectionPath)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot docSnap : queryDocumentSnapshots) {
                        docSnap.getReference().delete();
                    }
                    onSuccessListener.onSuccess(null);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user The User object to add.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void addUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    /**
     * Deletes a user from the Firestore database.
     *
     * @param userId The ID of the user to delete.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void deleteUser(final String userId, final OnSuccessListener<Void> onSuccessListener, final OnFailureListener onFailureListener) {
        final WriteBatch batch = db.batch();
        final DocumentReference userDocRef = db.collection("Users").document(userId);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        String[] subcollectionsPaths = {"Notifications", "Attendees"};
        for (String subcollectionPath : subcollectionsPaths) {
            tasks.add(userDocRef.collection(subcollectionPath).get());
        }

        tasks.add(db.collection("CheckIn").whereEqualTo("userId", userId).get());

        tasks.add(db.collection("SignUp").whereEqualTo("userId", userId).get());
        Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> list) {

                for (Object obj : list) {
                    QuerySnapshot querySnapshot = (QuerySnapshot) obj;
                    for (DocumentSnapshot docSnap : querySnapshot.getDocuments()) {
                        batch.delete(docSnap.getReference());
                    }
                }

                batch.delete(userDocRef);
                batch.commit().addOnSuccessListener(onSuccessListener)
                        .addOnFailureListener(onFailureListener);
            }
        }).addOnFailureListener(onFailureListener);

        db.collection("Users").document(userId)
                .delete()
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
    public static void addCheckIn(CheckIn checkIn, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("CheckIn")
                .document(UUID.randomUUID().toString())
                .set(checkIn)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Deletes an image from both Firestore database and Firebase Storage.
     *
     * @param imageId The ID of the image to delete from Firestore.
     * @param imagePath The path of the image file to delete from Firebase Storage.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void deleteImage(String imageId, String imagePath, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        // Delete the reference from Firestore
        db.collection("Images").document(imageId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Upon successful deletion from Firestore, delete from Firebase Storage
                    StorageReference imageRef = storage.getReference().child(imagePath);
                    imageRef.delete()
                            .addOnSuccessListener(onSuccessListener)
                            .addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Sends a notification related to an event through Firestore.
     *
     * @param notification The Notification object to send.
     * @param onSuccessListener Callback for successful operation.
     * @param onFailureListener Callback for operation failure.
     */
    public static void addNotification(Notification notification, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Events").document(notification.getEventId()).collection("Notifications")
                .document(notification.getId())
                .set(notification)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    /**
     * Get FCM token to send notification.
     *
     * @param event Event where FCM token will be saved
     */
    public static void getFCMTokenID(Event event){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete( Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                    return;
                }
                String idToken = task.getResult();
                HashMap<String, Object> notificationTokenId = new HashMap<>();
                notificationTokenId.put("tokenId", idToken);
                // add attendee id as one of the input when its setup
                FirebaseUtil.addUserTokenIdNotification(event, notificationTokenId, aVoid -> {}, e -> {});
            }});
    }
    /**
     * When details is modified copy existing FCM token ids along with it.
     *
     * @param eventId1 Event id to move FCM token from.
     * @param eventId2 Event id to move FCM token to.
     */
    public static void shareFCMToken(String eventId1, String eventId2){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sourceCollection = db.collection("Events").document(eventId1).collection("Notification User TokenID");

        sourceCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Task<Void>> writeTasks = new ArrayList<>();

                // Step 2: Write each Notification Token to Event ID 2
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> data = document.getData();
                    Task<Void> writeTask = db.collection("Events").document(eventId2).collection("Notification User TokenID")
                            .document(document.getId())
                            .set(data);
                    writeTasks.add(writeTask);
                }
                Tasks.whenAllSuccess(writeTasks).addOnSuccessListener(results -> {
                    Log.d("MoveNotificationTokens", "All Notification Tokens moved successfully.");
                }).addOnFailureListener(e -> {
                    Log.e("MoveNotificationTokens", "Error moving Notification Tokens.", e);
                });
            } else {
                Log.e("MoveNotificationTokens", "Error reading Notification Tokens.", task.getException());
            }
        });
    }

    /**
     * When details is modified copy existing FCM token ids along with it.
     *
     * @param event Event id to add notificatiin token id to.
     * @param notificationTokenId new notification token id
     * @param onSuccessListener Callback for successful upload, returns the image download URL.
     * @param onFailureListener Callback for upload failure.
     */
    public static void addUserTokenIdNotification(Event event, Map<String, Object> notificationTokenId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        String tokenIdToCheck = (String) notificationTokenId.get("tokenId"); // Assuming the key for the token ID in the map is "tokenId"
        CollectionReference tokenCollectionRef = db.collection("Events").document(event.getId()).collection("Notification User TokenID");
        tokenCollectionRef.whereEqualTo("tokenId", tokenIdToCheck).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && task.getResult().isEmpty()) {
                    tokenCollectionRef.document(String.valueOf(System.currentTimeMillis())) // Or use a more specific document ID strategy
                            .set(notificationTokenId)
                            .addOnSuccessListener(onSuccessListener)
                            .addOnFailureListener(onFailureListener);
                } else {
                    onFailureListener.onFailure(new Exception("Token ID already exists."));
                }
            } else {
                onFailureListener.onFailure(task.getException());
            }
        });
    }


    public static void addSignUp(SignUp signUp, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("SignUp")
                .document(UUID.randomUUID().toString())
                .set(signUp)
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

    public static void updateImage(String collectionName, String documentId, String fieldName,String imageString, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){
        db.collection(collectionName).document(documentId)
                .update(fieldName, imageString)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Citation: OpenAI, ChatGPT 4, 2024
    // Prompt: How to fetch collection from Firestore database?
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
    // end of citation

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

    public static void updateUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("Users")
                .document(user.getId())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public static FirebaseFirestore getDb() {
        return db;
    }

    public static List<List<String>> splitListIntoBatches(List<String> originalList, int batchSize) {
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < originalList.size(); i += batchSize) {
            int end = Math.min(originalList.size(), i + batchSize);
            List<String> batch = originalList.subList(i, end);
            batches.add(batch);
        }
        return batches;
    }

}
