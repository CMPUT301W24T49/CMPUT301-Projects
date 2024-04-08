package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.AttendeeArrayAdapter;
import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckInListFragment extends Fragment {
    public Event event;

    ArrayList<String> attendeeDataList;
    AttendeeArrayAdapter attendeeArrayAdapter;

    public CheckInListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_checkin_list, container, false);
        event = (Event) getArguments().getSerializable("Event"); // Retrieve event from event detail page

        // Initializations
        ListView listView = view.findViewById(R.id.attendee_listview);
        Button btnClose = view.findViewById(R.id.btn_close_checkin_list);

        // Initialize attendee data list and attendee array adapter
        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        fetchCheckIns();

        // Clicking a user opens a popup with user details (number of check-ins, location, timestamp)
        listView.setOnItemClickListener((adapterView, view1, position, rowId) -> {
            String user = attendeeDataList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);

            CheckInDetailFragment checkInDetail = new CheckInDetailFragment();
            checkInDetail.setArguments(bundle); // Pass event data to event detail page

            // Navigate to event detail page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, checkInDetail)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }

        });
        
        // Close button to go back to the previous screen
        btnClose.setOnClickListener(v -> {
            // Check if the fragment is added to an activity and if the activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    // Fetch check-ins (attendees) from Firebase and add them to the check-in list
    // Filter through the check-in list and add userIds checked into the clicked event
    private void fetchCheckIns() {
        Set<String> userIdsSet = new HashSet<>();                // Check-in list as a set removes duplicate userIds
        Map<String, Integer> userCheckInCount = new HashMap<>(); // Users are mapped to their number of check-ins
        FirebaseUtil.fetchCollection("CheckIn", CheckIn.class, new FirebaseUtil.OnCollectionFetchedListener<CheckIn>() {
            @Override
            public void onCollectionFetched(List<CheckIn> checkInList) {
                // If check-ins match the eventId, add to userIds list
                // citation: OpenAI, ChatGPT 4, 2024
                // Prompt:  I want to only get checkIns with the eventId of the clicked event
                // in the fetchCheckIns() method. How can I do this with a for loop?
                for (CheckIn checkIn : checkInList) {
                    if (event.getId().equals(checkIn.getEventId())) {
                        userIdsSet.add(checkIn.getUserId());
                        // End of citation

                        // Maps each user to their number of check-ins
                        userCheckInCount.put(checkIn.getUserId(), userCheckInCount.getOrDefault(checkIn.getUserId(), 0) + 1);
                    }
                }

                // citation: OpenAI, ChatGPT 4, 2024
                // Prompt: How do i update the checkInCount TextView with the number of check-ins?
                getActivity().runOnUiThread(() -> {
                    int checkInCount = userIdsSet.size();   // Number of unique check-ins
                    TextView checkInCounts = getView().findViewById(R.id.txt_checkin_count);
                    checkInCounts.setText("Checked-in users: " + checkInCount);
                });
                // End of citation

                attendeeDataList.addAll(userIdsSet);            // Add userIds to data list
                attendeeArrayAdapter.notifyDataSetChanged();    // Update attendee array adapter
            }

            @Override
            public void onError(Exception e) {
                Log.e("CheckInListFragment", "Error fetching check-ins: ", e); // Log error
            }
        });
    }

}

