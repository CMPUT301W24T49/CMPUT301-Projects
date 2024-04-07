package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.AttendeeArrayAdapter;
import com.example.qr.models.SignUp;
import com.example.qr.models.Event;
import com.example.qr.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

public class SignUpListFragment extends Fragment {
    public Event event;

    ArrayList<String> attendeeDataList;
    AttendeeArrayAdapter attendeeArrayAdapter;


    public SignUpListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_signup_list, container, false);
        event = (Event) getArguments().getSerializable("Event"); // Retrieve event from event detail page

        // Button initialization
        ListView listView = view.findViewById(R.id.attendee_listview);
        Button btnClose = view.findViewById(R.id.btn_close_signup_list);

        // Initialize attendee data list and attendee array adapter
        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        fetchSignUps();

        // Close button to go back to the previous screen
        btnClose.setOnClickListener(v -> {
            // Check if the fragment is added to an activity and if the activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    // Fetch signed up attendees from Firebase and add them to the sign up list
    // Filter through signUpList and add userIds checked into the clicked event
    private void fetchSignUps() {
        List<String> userIds = new ArrayList<>();               // Initialize userIds list
        FirebaseUtil.fetchCollection("SignUps", SignUp.class, new FirebaseUtil.OnCollectionFetchedListener<SignUp>() {
            @Override
            public void onCollectionFetched(List<SignUp> signUpList) {

                Log.d("SignUpListFragment", "Fetched sign-ups: " + signUpList.toString()); // Log fetched sign-ups
                // If sign-ups match the eventId, add to userIds list
                for (SignUp signUp : signUpList) {
                    if (signUp.getEventId().equals(event.getId())) {
                        userIds.add(signUp.getUserId());
                    }
                }

                attendeeDataList.addAll(userIds);               // Add userIds to data list
                attendeeArrayAdapter.notifyDataSetChanged();    // Update attendee array adapter

                // Print attendee data list
                for (String userId : userIds) {
                    Log.d("AttendeeListFragment", "userId: " + userId);
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });

    }



}
