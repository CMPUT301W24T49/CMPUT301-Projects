package com.example.qr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.ImagePickerUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class CreateEventFragment extends Fragment {
    private ImageView eventPoster;
    private EditText eventTitle, eventLocation;
    private Button btnUseExistingQr, btnGenerateQr, btnCancel;

    private ImagePickerUtil image;
    private CollectionReference eventsRef;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        super.onCreate(savedInstanceState);

        // Initialize your views
        eventPoster = view.findViewById(R.id.ivEventPoster);
        eventTitle = view.findViewById(R.id.eventTitle);
        eventLocation = view.findViewById(R.id.eventLocation);

        btnUseExistingQr = view.findViewById(R.id.btnUseExistingQr);
        btnGenerateQr = view.findViewById(R.id.btnGenerateQr);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnUseExistingQr.setOnClickListener(v -> {
            ReuseQrCodeFragment reuseQrCodeFragment = new ReuseQrCodeFragment();

            Bundle args = new Bundle();
            args.putString("name", eventTitle.getText().toString());
            args.putString("location", eventLocation.getText().toString());
            reuseQrCodeFragment.setArguments(args);

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, reuseQrCodeFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnGenerateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if textviews are empty
                if (eventTitle.getText().toString().isEmpty() || eventLocation.getText().toString().isEmpty()) {
                    //display error message
                    return;
                }
                // Handle the create button click
                //Generate a random event id string


                String eventId = "event" + System.currentTimeMillis();
                FirebaseUtil.addEvent(new Event (eventId, eventTitle.getText().toString(), "", "", new Date(), new GeoPoint(0, 0)
                , eventId, "", 0),
                        documentReference -> {
                        // Handle successful event creation

                        // Create a new instance of OrganizerFragment
                        OrganizerFragment organizerFragment = new OrganizerFragment();

                        // Perform the fragment transaction
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, organizerFragment);

                        fragmentTransaction.commit(); // Commit the transaction
                }, e -> {
                    // Handle event creation failure
                });

            }
        });

        // citation: OpenAI, ChatGPT 4, 2024: How do I click a button to change fragments
        // in android studio
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of OrganizerFragment
                OrganizerFragment organizerFragment = new OrganizerFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Use getFragmentManager() in a Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with OrganizerFragment. Assume R.id.fragment_container is the ID of your FrameLayout
                fragmentTransaction.replace(R.id.fragment_container, organizerFragment);
                // fragmentTransaction.addToBackStack(null); // Optional: Add this transaction to the back stack
                fragmentTransaction.commit(); // Commit the transaction
            }
        });
        // end citation



        // Add listeners or any additional initialization for other views as needed
        return view;
    }

    private void createEvent() {
        // Implement your logic to handle event creation
        // This could involve reading from the EditText fields, etc.
    }
}
