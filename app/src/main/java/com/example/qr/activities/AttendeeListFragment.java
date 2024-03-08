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
import com.example.qr.models.Event;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;


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
       // Button btnClose = view.findViewById(R.id.close_attendee_list_btn);

        eventId = getArguments().getString("Id"); // retrieve eventId data from EventListOrganizerFragment

        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        List<String> userIds = fetchCheckIns(); // store list of userIds
        attendeeDataList.addAll(userIds);
        attendeeArrayAdapter.notifyDataSetChanged();

        return view;
    }

    // Fetch checkIns (attendees) from Firebase and add them to checkInList
    private List<String> fetchCheckIns() {
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
            }

            @Override
            public void onError(Exception e) {
            }
        });

        return userIds;
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

