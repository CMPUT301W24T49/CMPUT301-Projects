package com.example.qr.activities;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.widget.Toast;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.SharedViewModel;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.GeolocationUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Random;

/**
 * MainActivity is the main activity for the application.
 */
public class MainActivity extends AppCompatActivity implements AdminUserProfileDetailFragment.UserDetailDialogListener, ImageDetailDialogFragment.ImageDetailDialogListener {
    private static final int RC_NOTIFICATION = 99;

    public static String androidId;
    @Override

    /**
     *
     * On create
     *
     * @param savedInstanceState  the saved instance state.
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setOrganizerNotificationStatus(Boolean.TRUE);

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

                /**
                 *
                 * On collection fetched
                 *
                 * @param userList  the user list.
                 */
                public void onCollectionFetched(List<User> userList) {

                    boolean userFound = false;
                    for (User user : userList) {
                        if (user.getId().equals(androidId)) {
                            userFound = true;
                            if (user.getRole().equals("admin")) {
                                AdministratorFragment adminMenuFragment = new AdministratorFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, adminMenuFragment).commit();
                            } else  {
                                // check the homepage of the user
                                if (user.getHomepage().equals("default")) {
                                    // Display the AttendeeFragment for the user
                                    GuestHomeFragment guestHomeFragment = new GuestHomeFragment();
                                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, guestHomeFragment).commit();
                                } else if (user.getHomepage().equals("organizer")) {
                                    Log.d("MainActivity", "Homepage is empty");
                                    OrganizerFragment organizerFragment = new OrganizerFragment();
                                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, organizerFragment).commit();
                                } else if (user.getHomepage().equals("attendee")) {
                                    AttendeeFragment attendeeFragment = new AttendeeFragment();
                                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, attendeeFragment).commit();
                                }
                            }
                            break;
                        }
                    }
                    if (!userFound) {
                        // Create a new user with a unique name and the androidId as the id field
                        String guestLastName= "" + new Random().nextInt(10000); // Generate a random number between 0 and 9999
                        String profilePicture = "https://github.com/identicons/guest.png";
                        User newUser = new User(androidId, "guest", guestLastName, "nonAdmin", profilePicture, "", "", "default");
                        // Add the new user to the database
                        FirebaseUtil.addUser(newUser, new OnSuccessListener<Void>() {
                        @Override

                        /**
                         *
                         * On success
                         *
                         * @param aVoid  the a void.
                         */
                        public void onSuccess(Void aVoid) {

                            // Display the AttendeeFragment for the new user
                            GuestHomeFragment guestHomeFragment = new GuestHomeFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, guestHomeFragment).commit();
                        }
                        }, new OnFailureListener() {
                            @Override

                            /**
                             *
                             * On failure
                             *
                             * @param e  the e.  It is NonNull
                             */
                            public void onFailure(@NonNull Exception e) {

                                Log.e("MainActivity", "Error adding new user", e);
                            }
                        });
                    }
                }

                @Override

                /**
                 *
                 * On error
                 *
                 * @param e  the e.
                 */
                public void onError(Exception e) {

                    Log.e("MainActivity", "Error fetching user collection", e);
                }
            });
        }
        if (Build. VERSION.SDK_INT >= Build. VERSION_CODES. TIRAMISU) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, RC_NOTIFICATION);
        }

        GeolocationUtil.getCurrentLocation(this, location -> {
            Log.d("MainActivity", "Location: " + location);
        });

        handleIntent(getIntent());
    }

    @Override

/**
 *
 * On new intent
 *
 * @param intent  the intent.
 */
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }


    /**
     *
     * Handle intent
     *
     * @param intent  the intent.
     */
    private void handleIntent(Intent intent) {

        if (intent != null && intent.hasExtra("open_fragment") &&
                "notification_list_fragment".equals(intent.getStringExtra("open_fragment"))) {
            String eventID = intent.getStringExtra("event_key");
            Event event = (Event) intent.getSerializableExtra("event");

            if (event != null) {
                openNotificationListFragment(event);
            } else {
                // Handle the case where the event is null
                Log.e("MainActivity", "Event object is null.");
            }
        }
    }


    /**
     *
     * Open notification list fragment
     *
     * @param event  the event.
     */
    private void openNotificationListFragment(Event event) {

        NotificationListFragment fragment = new NotificationListFragment();
        Bundle args = new Bundle();
        args.putSerializable("event_key", event.getId()); // Make sure the event ID is serialized properly
        args.putSerializable("event", event);
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override

/**
 *
 * On request permissions result
 *
 * @param requestCode  the request code.
 * @param permissions  the permissions.
 * @param grantResults  the grant results.
 */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Notification permission is required to send you important updates.", Toast.LENGTH_LONG).show();
            }
        }
    }
}

