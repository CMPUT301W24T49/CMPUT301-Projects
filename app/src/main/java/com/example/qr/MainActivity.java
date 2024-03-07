// Jai Shakti Mata
package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for now, im setting the main menu to the attendee main menu
        // feel free to change it to the organizer main menu if you want to preview your UI
        // feel free to change it to the admin main menu if you want to preview your UI
        // example: setContentView(R.layout.organizer_main_menu);
        // example: setContentView(R.layout.admin_main_menu);
//        setContentView(R.layout.attendee_main_menu);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state, then we don't need to do anything
            // and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of the HomeFragment
            HomeFragment firstFragment = new HomeFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }
}