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
import com.example.qr.models.CheckIn;
import com.example.qr.models.SignUp;
import com.example.qr.models.Event;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizerSignUpListFragment extends Fragment {
    public Event event;

    ArrayList<User> attendeeDataList;
    AttendeeArrayAdapter attendeeArrayAdapter;

    public OrganizerSignUpListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_organizer_signup_list, container, false);
        event = (Event) getArguments().getSerializable("Event"); // Retrieve event from event detail page

        // Button initialization
        ListView listView = view.findViewById(R.id.attendee_listview);
        Button btnClose = view.findViewById(R.id.btn_close_signup_list);

        // Initialize attendee data list and attendee array adapter
        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        fetchSignUps();

        // Clicking a user opens a popup with user details (number of check-ins, location, timestamp)
        listView.setOnItemClickListener((adapterView, view1, position, rowId) -> {
            User user = attendeeDataList.get(position);

            SignUp userCheckInData = userSignUpsMap.getOrDefault(user.getId(), new ArrayList<>());

            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);   // User data
            bundle.putSerializable("CheckIns", (Serializable) userCheckInData);  // Check-in data

            OrganizerCheckInDetailFragment checkInDetail = new OrganizerCheckInDetailFragment();
            checkInDetail.setArguments(bundle); // Pass user data to user detail page

            if (getFragmentManager() != null) {
                checkInDetail.show(getFragmentManager(), "CheckInDetail");  // Show user detail dialog
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

    // Fetch signed up attendees from Firebase and add them to the sign up list
    // Filter through signUpList and add userIds checked into the clicked event
    private void fetchSignUps() {
        List<String> userIds = new ArrayList<>();           // Initialize userIds list
        FirebaseUtil.fetchCollection("SignUp", SignUp.class, new FirebaseUtil.OnCollectionFetchedListener<SignUp>() {
            @Override
            public void onCollectionFetched(List<SignUp> signUpList) {
                // If sign-ups match the eventId, add to userIds list
                for (SignUp signUp : signUpList) {
                    if (event.getId().equals(signUp.getEventId())) {
                        userIds.add(signUp.getUserId());
                    }
                }
                // Fetch signed up users and add them to the attendee data list
                FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
                    @Override
                    public void onCollectionFetched(List<User> userList) {
                        // Filter through the user list and add userIds to the data list
                        for (User user : userList) {
                            if (userIds.contains(user.getId())) {
                                attendeeDataList.add(user);
                            }
                        }
                        attendeeArrayAdapter.notifyDataSetChanged();    // Update attendee array adapter
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("CheckInListFragment", "Error fetching users: ", e); // Log error
                    }
                });

                attendeeArrayAdapter.notifyDataSetChanged();    // Update attendee array adapter

                // Print attendee data list
                for (String userId : userIds) {
                    Log.d("AttendeeListFragment", "userId: " + userId);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("CheckInListFragment", "Error fetching sign-ups: ", e); // Log error
            }
        });

    }



}
