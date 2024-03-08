package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
//import com.example.qr.models.AttendeesArrayAdapter;


import java.util.ArrayList;

public class AttendeeListFragment extends Fragment {

    ListView attendeesList;
 //   ArrayList<Attendee> attendeeArrayList;
 //   AttendeesArrayAdapter attendeesArrayAdapter;

    public AttendeeListFragment() {
        // Required empty public constructor
    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_attendee_list, container, false);
//
//        attendeesList = view.findViewById(R.id.listView);
//        attendees = new ArrayList<>();
//        attendeeArrayAdapter = new AttendeeArrayAdapter(getActivity(), attendees);
//        listView.setAdapter(attendeeArrayAdapter);
//
//
//        fetchAttendees(); // Implement this method to fetch attendees from Firebase and add them to attendeeList
//
//        return view;
//    }


//  //  private void fetchAttendees() {
//        FirebaseUtil.getEventAttendees(eventId, new FirestoreCallback<List<Attendee>>() {
//            @Override
//            public void onCallback(List<Attendee> attendees) {
//                attendeeList.clear();
//                attendeeList.addAll(attendees);
//                adapter.notifyDataSetChanged(); // Notify the adapter to refresh the ListView
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // Handles error
//            }
//        });
//    }
//
//    }







}
