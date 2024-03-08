package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateEventFragment extends Fragment {
    private ImageView eventPoster;
    private EditText eventTitle, eventLocation;
    private Button btnUseExistingQr, btnGenerateQr, btnCreate, btnCancel;

    private FirebaseFirestore db;
    private CollectionReference eventsRef;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("Event");

        // Initialize your views
        eventPoster = view.findViewById(R.id.ivEventPoster);
        eventTitle = view.findViewById(R.id.eventTitle);
        eventLocation = view.findViewById(R.id.eventLocation);

        btnUseExistingQr = view.findViewById(R.id.btnUseExistingQr);
        btnGenerateQr = view.findViewById(R.id.btnGenerateQr);
        btnCreate = view.findViewById(R.id.btnCreate);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnUseExistingQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the create button click
                createEvent();
            }
        });

        btnGenerateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the create button click
                createEvent();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the create button click
                createEvent();
            }
        });

        // Example: Set a click listener on the Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the cancel button click

            }
        });

        // Add listeners or any additional initialization for other views as needed
        return view;
    }

    private void createEvent() {
        // Implement your logic to handle event creation
        // This could involve reading from the EditText fields, etc.
    }
}
