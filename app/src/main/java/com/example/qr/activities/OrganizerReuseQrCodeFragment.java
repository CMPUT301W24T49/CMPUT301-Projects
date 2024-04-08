package com.example.qr.activities;

import static com.example.qr.utils.FirebaseUtil.uploadImageAndGetUrl;

import android.graphics.Bitmap;
import android.net.Uri;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventSpinnerAdapter;
import com.example.qr.models.Notification;
import com.example.qr.models.SharedViewModel;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.GenerateQRCode;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrganizerReuseQrCodeFragment extends Fragment {

    private Button btnCancel, chooseQrCode;
    private Spinner eventSpinner;

    private ImageView eventQrCode;
    private ArrayList<Event> eventDataList;

    private ArrayAdapter<Event> eventAdapter;

    private Event selectedEvent;
    private Uri imageUri;

    private String qrCode;
    private Integer prevMaxAttendees;

    private Date prevStartDate, prevEndDate;

    public OrganizerReuseQrCodeFragment() {
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

        //get event object and uri if it exists

        selectedEvent = (Event) getArguments().getSerializable("Event");

        if(getArguments().getParcelable("ImageUri") != null){
            imageUri = (Uri) getArguments().getParcelable("ImageUri");
        }

        // Setup ArrayAdapter using the default spinner layout and your events list
        eventAdapter = new EventSpinnerAdapter(getContext(), eventDataList);
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
                qrCode = eventDataList.get(position).getId();
                if(qrCode != null) {
                    Bitmap img = GenerateQRCode.generateQR(qrCode);
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
                OrganizerCreateEventFragment organizerCreateEventFragment = new OrganizerCreateEventFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, organizerCreateEventFragment);

                fragmentTransaction.commit(); // Commit the transaction
            }
        });

        chooseQrCode.setOnClickListener(v -> {

            if(eventSpinner.getSelectedItemPosition() == 0){
                Toast.makeText(getContext(), "Please select an event", Toast.LENGTH_SHORT).show();
                return;
            }

            if(qrCode != null) {
                selectedEvent.setQrCode(qrCode);
            }


            if(imageUri != null) {
                uploadImageAndGetUrl(imageUri, UUID.randomUUID().toString(), downloadUrl -> {
                    Log.d("CreateEvent", "Uploaded image: " + downloadUrl.toString());
                    selectedEvent.setEventPoster(downloadUrl.toString());
                    FirebaseUtil.addEvent(selectedEvent,
                            aVoid -> {
                                // GPT given code to switch back to organizer screen
                                switchToOrganizerFragment();
                                Toast.makeText(getContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                            }, e -> {
                                // else toast that it failed to create
                                Toast.makeText(getContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
                            });

                }, e -> {
                    Log.e("CreateEvent", "Failed to upload image", e);
                    FirebaseUtil.addEvent(selectedEvent,
                            aVoid -> {
                                // GPT given code to switch back to organizer screen
                                switchToOrganizerFragment();
                                Toast.makeText(getContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                            }, ee -> {
                                // else toast that it failed to create
                                Toast.makeText(getContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
                            });
                });
            }else {
                FirebaseUtil.addEvent(selectedEvent,
                        aVoid -> {
                            // GPT given code to switch back to organizer screen
                            switchToOrganizerFragment();
                            Toast.makeText(getContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                        }, ee -> {
                            // else toast that it failed to create
                            Toast.makeText(getContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
                        });
            }
            // DO NOT REMOVE THIS. ITS FOR PUSH NOTIFICATION
            FirebaseUtil.shareFCMToken(selectedEvent.getQrCode(), selectedEvent.getId());
            String message = selectedEvent.getTitle() + "'s details has been updated by organizer.";
            Notification notification = new Notification("notification" + System.currentTimeMillis(), selectedEvent.getId(), message, new Date(), false);
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

    private void switchToOrganizerFragment() {
        OrganizerFragment organizerFragment = new OrganizerFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, organizerFragment);
        fragmentTransaction.commit();
    }
}
