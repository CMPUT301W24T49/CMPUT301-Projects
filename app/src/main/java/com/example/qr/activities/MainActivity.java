package com.example.qr.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements EventDetailFragment.EventDetailDialogListener, AdminUserProfileDetailFragment.UserDetailDialogListener, ImageDetailDialogFragment.ImageDetailDialogListener {
    EventArrayAdapter eventArrayAdapter;

    public static String androidId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Get the Android ID of the device
            androidId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            //log it
            Log.d("MainActivity", "Android ID: " + androidId);
            // Use the fetchCollection function to get the user associated with this Android ID
            FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
                @Override
                public void onCollectionFetched(List<User> userList) {
                    boolean userFound = false;
                    for (User user : userList) {
                        if (user.getId().equals(androidId)) {
                            userFound = true;
                            if (user.getRole().equals("admin")) {
                                AdministratorFragment adminMenuFragment = new AdministratorFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, adminMenuFragment).commit();
                            } else if (user.getRole().equals("attendee")) {
                                AttendeeFragment attendeeFragment = new AttendeeFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, attendeeFragment).commit();
                            } else if (user.getRole().equals("organizer")) {
                                OrganizerFragment organizerFragment = new OrganizerFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, organizerFragment).commit();
                            }
                            break;
                        }
                    }
                    if (!userFound) {
                        // Create a new user with a unique name and the androidId as the id field
                        String guestLastName= "" + new Random().nextInt(10000); // Generate a random number between 0 and 9999
                        String profilePicture = "https://github.com/identicons/guest.png";
                        User newUser = new User(androidId, "guest", guestLastName, "attendee", profilePicture, "", "", "");
                        // Add the new user to the database
                        FirebaseUtil.addUser(newUser, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Display the AttendeeFragment for the new user
                                AttendeeFragment attendeeFragment = new AttendeeFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, attendeeFragment).commit();
                            }
                        }, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("MainActivity", "Error adding new user", e);
                            }
                        });
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("MainActivity", "Error fetching user collection", e);
                }
            });
        }
    }



    @Override
    public void onDeleteEvent(Event event) {
        // Code to delete the event goes here
        // You may need to communicate with your database or a ViewModel to perform the deletion
    }

    @Override
    public void onDeleteUser(User user){

    }

}