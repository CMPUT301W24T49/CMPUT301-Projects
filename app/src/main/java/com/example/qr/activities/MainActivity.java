package com.example.qr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.User;
import com.example.qr.models.Image;
import com.example.qr.utils.FirebaseUtil;

public class MainActivity extends AppCompatActivity implements EventDetailFragment.EventDetailDialogListener, AdminUserProfileDetailFragment.UserDetailDialogListener, ImageDetailDialogFragment.ImageDetailDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for now, im setting the main menu to the attendee main menu
        // feel free to change it to the organizer main menu if you want to preview your UI
        // feel free to change it to the admin main menu if you want to preview your UI
        // example: setContentView(R.layout.organizer_main_menu);
        // example: setContentView(R.layout.admin_main_menu);
        //setContentView(R.layout.attendee_main_menu);

        // this for admin part
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            HomeFragment firstFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }


    }
    @Override
    public void onDeleteEvent(String eventId) {
        // Code to delete the event goes here
        // You may need to communicate with your database or a ViewModel to perform the deletion
        FirebaseUtil.deleteEvent(eventId,
                aVoid -> {
                    // Success callback
                    Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                    // Handle the rest of the success scenario, like updating the UI or finishing an activity.
                },
                e -> {
                    // Failure callback
                    Toast.makeText(this, "Failed to delete event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Handle the failure scenario, like showing an error message to the user.
                });
    }


    @Override
    public void onDeleteUser(String userId){
        FirebaseUtil.deleteUser(userId,
                aVoid -> {
                    // This code will be executed on successful deletion
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    // Further actions to handle the user interface after deletion can be added here,
                    // such as removing the item from a list or returning to the previous screen.
                },
                e -> {
                    // This code will be executed if there's a failure during deletion
                    Toast.makeText(this, "Failed to delete user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    // You might want to handle the error, maybe by showing an error message
                    // or retrying the operation.
                });
    }
    @Override
    public void onDeleteImage(Image image){

    }
}