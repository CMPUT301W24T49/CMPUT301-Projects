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
import com.example.qr.utils.FirebaseUtil;


import java.util.ArrayList;
import java.util.List;

public class AttendeeListFragment extends Fragment {

    ListView attendeeList;
    ArrayList<String> attendeeDataList;
    AttendeeArrayAdapter attendeeArrayAdapter;

    String eventId;

    public AttendeeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendee_list, container, false);

        ListView listView = view.findViewById(R.id.attendee_listview);
        Button btnClose = view.findViewById(R.id.btn_close_attendee_list);

        eventId = getArguments().getString("Id"); // Retrieve eventId data from EventListOrganizerFragment

        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        fetchCheckIns(); // Store list of userIds

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    // Fetch checkIns (attendees) from Firebase and add them to checkInList
    // Filter through checkInList and add userIds checked into the clicked event
    // return list of userIds
    private void fetchCheckIns() {
        // Fetch attendee data from Firebase
        List<String> userIds = new ArrayList<>();
        FirebaseUtil.fetchCollection("CheckIns", CheckIn.class, new FirebaseUtil.OnCollectionFetchedListener<CheckIn>() {
            @Override
            public void onCollectionFetched(List<CheckIn> checkInList) {
                // If check ins match the eventId, add to userIds list
                for (CheckIn checkIn : checkInList) {
                    if (eventId.equals(checkIn.getEventId())) {
                        userIds.add(checkIn.getUserId());
                    }
                }
                attendeeDataList.addAll(userIds);   // Add userIds to data list
                attendeeArrayAdapter.notifyDataSetChanged();
                //print attendee data list
                for (String userId : userIds) {
                    Log.d("AttendeeListFragment", "userId: " + userId);
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });

    }

//    private void fetchUsers(List<String> userIdList) {
//        List<String> newUserList = new ArrayList<>();
//        FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
//            @Override
//            public void onCollectionFetched(List<User> userList) {
//                // Only get checkIns with eventId Attendee
//                for (User user : userList) {
//                    if (user.getId()  ) {
//                        newUserList.add(user.getName());
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//            }
//        });
//    }

}

