package com.example.qr.activities;

import static com.example.qr.activities.MainActivity.androidId;
import static com.example.qr.utils.FirebaseUtil.splitListIntoBatches;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.SignUp;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AttendeeMyEventListFragment extends Fragment {

    ArrayList<Event> eventDataList;
    EventArrayAdapter eventArrayAdapter;

    RelativeLayout fragmentLayout;


    public AttendeeMyEventListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        fragmentLayout = view.findViewById(R.id.fragment_event_list_layout);

        // Button initialization
        ListView listView = view.findViewById(R.id.listview_events);
        Button btnClose = view.findViewById(R.id.btn_close_event_list);
        
        eventDataList = new ArrayList<>();
        eventArrayAdapter = new EventArrayAdapter(getContext(), eventDataList);
        listView.setAdapter(eventArrayAdapter);

        fetchDataOptimized();


        // Event list onclick directs to AttendeeListFragment
        listView.setOnItemClickListener((adapterView, view1, position, rowId) -> {

            // Get position and Id of event clicked
            Event event = eventDataList.get(position);

            // Send eventId to AttendeeListFragment
            // Adapted from answer given by João Marcos
            // https://stackoverflow.com/questions/24555417/how-to-send-data-from-one-fragment-to-another-fragment
            Bundle args = new Bundle();
            args.putSerializable("Event", event);
            args.putSerializable("NoSignUp", true);

            AttendeeEventDetailFragment attendeeEventDetailFragment = new AttendeeEventDetailFragment();
            attendeeEventDetailFragment.setArguments(args); // Pass data to attendeeListFragment
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, attendeeEventDetailFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }

        });

        // Close button going back previous screen
        btnClose.setOnClickListener(v -> {
            // Check if fragment is added to an activity and if activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });


        fragmentLayout.setVisibility(View.GONE);

        return view;
    }

    // Fetch events from Firebase and add them to eventList
    private void fetchData() {
        // Citation: OpenAI, ChatGPT 4, 2024
        // Prompt: How would i use fetchCollection to fetch event data?

        List<String> eventIds = new ArrayList<>();

        FirebaseUtil.fetchCollection("SignUp", SignUp.class, new FirebaseUtil.OnCollectionFetchedListener<SignUp>() {
            @Override
            public void onCollectionFetched(List<SignUp> signUpList) {
                //only get the event ids that the user has signed up for
                for (SignUp signUp : signUpList) {
                    if (signUp.getUserId().equals(androidId)) {
                        eventIds.add(signUp.getEventId());
                        Log.d("AttendeeMyEventListFragment", "Fetched " + signUp.getEventId());
                    }
                }

                FirebaseUtil.fetchCollection("CheckIn", CheckIn.class, new FirebaseUtil.OnCollectionFetchedListener<CheckIn>() {
                    @Override
                    public void onCollectionFetched(List<CheckIn> CheckInList) {
                        //only get the event ids that the user has checked in for
                        for (CheckIn checkIn : CheckInList) {
                            if (checkIn.getUserId().equals(androidId)) {
                                eventIds.add(checkIn.getEventId());
                                Log.d("AttendeeMyEventListFragment", "Fetched " + checkIn.getEventId());
                            }
                        }

                        //fetch all events with the id and add them to the list
                        FirebaseUtil.fetchCollection("Events", Event.class, new FirebaseUtil.OnCollectionFetchedListener<Event>() {
                            @Override
                            public void onCollectionFetched(List<Event> eventList) {
                                // Handle the fetched events
                                for (Event event : eventList) {
                                    if (eventIds.contains(event.getId())) {
                                        event.setTitle(event.getTitle());
                                        eventDataList.add(event);
                                    }
                                }
                                eventArrayAdapter.notifyDataSetChanged();   // Update event array adapter
                                Log.d("EventListFragment", "Fetched " + eventList.size() + " events");
                            }

                            @Override
                            public void onError(Exception e) {
                                //log error
                                Log.d("AttendeeMyEventListFragment", "Failed to fetch events");
                            }
                        });
                    }
                    @Override
                    public void onError(Exception e) {
                        //log error
                        Log.d("AttendeeMyEventListFragment", "Failed to fetch checkins");
                    }
                    // End of citation
                });
            }
            @Override
            public void onError(Exception e) {
                //log error
                Log.d("AttendeeMyEventListFragment", "Failed to fetch signups");

            }
            // End of citation
        });
    }

    private void fetchDataOptimized() {
        List<String> eventIds = new ArrayList<>();

        // Fetch SignUps for the current user
        FirebaseUtil.getDb().collection("SignUp")
                .whereEqualTo("userId", androidId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        SignUp signUp = documentSnapshot.toObject(SignUp.class);
                        eventIds.add(signUp.getEventId());
                    }

                    if(eventIds.isEmpty()){
                        fragmentLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        fetchEvents(eventIds);
                    }

                }).addOnFailureListener(e -> {
                    // Handle error, possibly decrementing batchesCompleted if you want to ensure all batches finish regardless of success
                });
    }

    private void fetchEvents(List<String> eventIds) {
        List<List<String>> batches = splitListIntoBatches(eventIds, 10); // Split event IDs into batches of 10
        AtomicInteger batchesCompleted = new AtomicInteger();
        List<Event> combinedEvents = new ArrayList<>();

        for (List<String> batch : batches) {
            FirebaseUtil.getDb().collection("Events")
                    .whereIn("id", batch)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Event event = documentSnapshot.toObject(Event.class);
                            combinedEvents.add(event);
                        }

                        if (batchesCompleted.incrementAndGet() == batches.size()) {
                            // All batches have completed
                            eventDataList.addAll(combinedEvents);
                            eventArrayAdapter.notifyDataSetChanged(); // Update UI
                        }

                        fragmentLayout.setVisibility(View.VISIBLE);

                    })
                    .addOnFailureListener(e -> {
                        // Handle error, possibly decrementing batchesCompleted if you want to ensure all batches finish regardless of success
                    });
        }
    }

}
