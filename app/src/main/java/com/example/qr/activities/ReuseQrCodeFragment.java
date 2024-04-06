package com.example.qr.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.Notification;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.GenerateQRCode;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReuseQrCodeFragment extends Fragment {

    private Button btnCancel, chooseQrCode;
    private Spinner eventSpinner;

    private ImageView eventQrCode;
    private ArrayList<Event> eventDataList;

    private ArrayAdapter<Event> eventAdapter;

    private String eventId, name, location, prevStartTime, prevEndTime;
    private Integer prevMaxAttendees;

    private Date prevStartDate, prevEndDate;

    public ReuseQrCodeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reuse_qr, container, false);
        super.onCreate(savedInstanceState);

        btnCancel = view.findViewById(R.id.btnCancel);

        eventSpinner = view.findViewById(R.id.eventSpinner);

        eventQrCode = view.findViewById(R.id.ivEventPoster);

        chooseQrCode = view.findViewById(R.id.chooseQrCodeButton);
        
        eventDataList = new ArrayList<>();

        // Get the name and location from the bundle
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
            location = args.getString("location");
            prevStartTime = args.getString("prevStartTime");
            prevEndTime = args.getString("prevEndTime");

        }

        // Setup ArrayAdapter using the default spinner layout and your events list
        eventAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, eventDataList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to your Spinner
        eventSpinner.setAdapter(eventAdapter);

        fetchData();

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if the placeholder "Select one:" is selected
                if(position == 0) {
                    // Placeholder selected, do nothing or reset the QR code image
                    eventQrCode.setImageResource(android.R.color.transparent); // Hide QR code
                    return;
                }

                // Your existing logic for when a real event is selected
                eventId = eventDataList.get(position).getId();
                if(eventId != null) {
                    Bitmap img = GenerateQRCode.generateQR(eventId);
                    eventQrCode.setImageBitmap(img);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // citation: OpenAI, ChatGPT 4, 2024: How do I click a button to change fragments
        // in android studio
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of OrganizerFragment
                CreateEventFragment createEventFragment = new CreateEventFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, createEventFragment);

                fragmentTransaction.commit(); // Commit the transaction
            }
        });

        chooseQrCode.setOnClickListener(v -> {
            Event event = new Event (eventId, name, "", "", new Date(), new Date(),
                    prevStartTime, prevEndTime, new GeoPoint(1,1), eventId, "", 0);
            FirebaseUtil.addEvent(event,
                    documentReference -> {

                        // Create a new instance of OrganizerFragment
                        OrganizerFragment organizerFragment = new OrganizerFragment();

                        // Perform the fragment transaction
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, organizerFragment);

                        fragmentTransaction.commit(); // Commit the transaction
                        Toast.makeText(getContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                    }, e -> {
                        // Handle event creation failure
                    });
            String message = event.getTitle() + "'s details has been updated by organizer.";
            Notification notification = new Notification("notification" + System.currentTimeMillis(), event.getId(), message, new Date(), false);
            FirebaseUtil.addNotification(notification, aVoid -> {}, e -> {});
        });
        // end citation

        return view;
    }

    private void fetchData() {

        // Placeholder for "Select one:"
        Event placeholderEvent = new Event();
        placeholderEvent.setTitle("Select one:");
        placeholderEvent.setId("");
        eventDataList.add(placeholderEvent);

        FirebaseUtil.fetchCollection("Events", Event.class, new FirebaseUtil.OnCollectionFetchedListener<Event>() {
            @Override
            public void onCollectionFetched(List<Event> eventList) {
                // Handle the fetched events here
                eventDataList.addAll(eventList);
                eventAdapter.notifyDataSetChanged();
                Log.d("EventListFragment", "Fetched " + eventList.size() + " events");
            }

            @Override
            public void onError(Exception e) {
                // Handle any errors here
            }


        });
    }
}
