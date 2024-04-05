package com.example.qr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;

import java.util.List;


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
                    // check which user has the same Android ID, androidId is the id of the user
                    for (User user : userList) {
                        if (user.getId().equals(androidId)) {
                            // If the user is an admin, display the AdminMenuFragment
                            // If the user is an attendee, display the AttendeeFragment
                            // If the user is an organizer, display the OrganizerFragment
                            if (user.getRole().equals("admin")) {
                                AdministratorFragment adminMenuFragment = new AdministratorFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, adminMenuFragment).commit();
                                break;
                            } else if (user.getRole().equals("attendee")) {
                                AttendeeFragment attendeeFragment = new AttendeeFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, attendeeFragment).commit();
                                break;
                            } else if (user.getRole().equals("organizer")) {
                                OrganizerFragment organizerFragment = new OrganizerFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, organizerFragment).commit();
                                break;
                            }
                        } else {
                            // If the user is not found, display the HomeFragment
                            HomeFragment homeFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
                        }
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