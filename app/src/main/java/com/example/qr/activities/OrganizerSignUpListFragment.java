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
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The class Organizer sign up list fragment extends fragment
 */
public class OrganizerSignUpListFragment extends Fragment {
    public Event event;

    ArrayList<User> attendeeDataList;
    AttendeeArrayAdapter attendeeArrayAdapter;
    Map<String, List<SignUp>> userSignUpsMap;


    /**
     *
     * Organizer sign up list fragment
     *
     * @return public
     */
    public OrganizerSignUpListFragment() {

        // Required empty public constructor
    }

    @Override

/**
 *
 * On create view
 *
 * @param inflater  the inflater.
 * @param container  the container.
 * @param savedInstanceState  the saved instance state.
 * @return View
 */
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

    /**
     *
     * Fetch sign ups
     *
     */
    private void fetchSignUps() {

        List<String> userIds = new ArrayList<>();           // Initialize userIds list
        userSignUpsMap = new HashMap<>();
        FirebaseUtil.fetchCollection("SignUp", SignUp.class, new FirebaseUtil.OnCollectionFetchedListener<SignUp>() {
            @Override

/**
 *
 * On collection fetched
 *
 * @param signUpList  the sign up list.
 */
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

/**
 *
 * On collection fetched
 *
 * @param userList  the user list.
 */
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

/**
 *
 * On error
 *
 * @param e  the e.
 */
                    public void onError(Exception e) {

                        Log.e("SignUpListFragment", "Error fetching users: ", e); // Log error
                    }
                });

                attendeeArrayAdapter.notifyDataSetChanged();    // Update attendee array adapter

                // Print attendee data list
                for (String userId : userIds) {
                    Log.d("AttendeeListFragment", "userId: " + userId);
                }
            }

            @Override

/**
 *
 * On error
 *
 * @param e  the e.
 */
            public void onError(Exception e) {

                Log.e("SignUpListFragment", "Error fetching sign-ups: ", e); // Log error
            }
        });

    }



}
