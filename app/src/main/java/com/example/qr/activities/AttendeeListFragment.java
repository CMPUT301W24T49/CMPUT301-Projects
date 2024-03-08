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
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

public class AttendeeListFragment extends Fragment {

    ListView attendeeList;
    ArrayList<User> attendeeDataList;
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

        eventId = getArguments().getString("Id"); // retrieve data from EventListOrganizerFragment

        attendeeDataList = new ArrayList<>();
        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendeeDataList);
        listView.setAdapter(attendeeArrayAdapter);

        fetchAttendees(); // Fetch attendees from Firebase and add them to attendeeList

        return view;
    }


    private void fetchAttendees() {
        // Fetch attendee data from Firebase
        FirebaseUtil.fetchCollection("CheckIns", CheckIn.class, new FirebaseUtil.OnCollectionFetchedListener<CheckIn>() {
            @Override
            public void onCollectionFetched(List<CheckIn> attendeeList) {

                // Handle the fetched events here
                attendeeDataList.addAll(attendeeList);
                attendeeArrayAdapter.notifyDataSetChanged();
                Log.d("AttendeeListFragment", "Fetched " + attendeeList.size() + " attendees");
            }

            @Override
            public void onError(Exception e) {
                // Handle any errors here
            }


        });
    }
}

