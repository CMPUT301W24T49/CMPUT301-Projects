package com.example.qr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.User;
import com.example.qr.models.Image;
import com.example.qr.utils.FirebaseUtil;

public class MainActivity extends AppCompatActivity implements EventDetailFragment.EventDetailDialogListener, AdminUserProfileDetailFragment.UserDetailDialogListener, ImageDetailDialogFragment.ImageDetailDialogListener {
    EventArrayAdapter eventArrayAdapter;
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

}