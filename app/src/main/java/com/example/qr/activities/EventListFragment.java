package com.example.qr.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.Notification;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * EventListFragment displays a list of events, allowing users to view detailed information or edit them.
 */
public class EventListFragment extends Fragment {

    ListView eventList;
    ArrayList<Event> eventDataList;
    EventArrayAdapter eventArrayAdapter;
    String message;
    Map<String, Object> notificationTokenId;

    private int positionToEdit;
    private FirebaseFirestore db;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        ListView listView = view.findViewById(R.id.listview_events);
        eventDataList = new ArrayList<>();
        eventArrayAdapter = new EventArrayAdapter(getContext(), eventDataList);
        listView.setAdapter(eventArrayAdapter);
        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;
                Event clickedEvent = (Event) adapterView.getAdapter().getItem(position);
                EventDetailFragment addCityFragment = EventDetailFragment.newInstance(clickedEvent);
                addCityFragment.setTargetFragment(EventListFragment.this, 0);
                addCityFragment.show(getParentFragmentManager(), "Event Detail");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventToBeDeleted = eventDataList.get(position);

                // Create an AlertDialog to confirm deletion
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Event") // Set the title of the dialog
                        .setMessage("Are you sure you want to delete " + eventToBeDeleted.getTitle() + "?") // Set the message to show to the user
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Notify attendee that event has been cancelled.
                            String message = eventToBeDeleted.getTitle() + " is cancelled.";
                            Notification notification = new Notification("notification" + System.currentTimeMillis(), eventToBeDeleted.getId(), message, new Date(), false);
                            FirebaseUtil.addNotification(notification, aVoid -> {}, e -> {});

                            // Call FirebaseUtil.deleteEvent to delete the event
                            FirebaseUtil.deleteEvent(eventToBeDeleted.getId(),
                                    aVoid -> {
                                        // Successfully deleted event from Firestore
                                        eventDataList.remove(position); // Remove event from the local list
                                        eventArrayAdapter.notifyDataSetChanged(); // Notify the adapter to update the list
                                        fetchData();
                                        // Optionally, show a confirmation message
                                        Toast.makeText(getActivity(), "Event " + eventToBeDeleted.getId() + " deleted successfully", Toast.LENGTH_SHORT).show();
                                    },
                                    e -> {
                                        Toast.makeText(getActivity(), "Failed to delete event", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("No", null) // Set the negative button with no additional actions
                        .setIcon(android.R.drawable.ic_dialog_alert) // Set an icon for the dialog
                        .show(); // Display the AlertDialog

                return true; // Return true to indicate that the click was handled
            }
        });


        // Close button to go back to the previous screen
//        btnClose.setOnClickListener(v -> {
//            // Check if the fragment is added to an activity and if the activity has a FragmentManager
//            if (isAdded() && getActivity() != null) {
//                getActivity().onBackPressed();
//            }
//
//        });

        return view;
    }

    // Fetch events from Firebase and add them to eventList
    private void fetchData() {
        FirebaseUtil.fetchCollection("Events", Event.class, new FirebaseUtil.OnCollectionFetchedListener<Event>() {
            @Override
            public void onCollectionFetched(List<Event> eventList) {
                // Handle the fetched events
                eventDataList.addAll(eventList);
                eventArrayAdapter.notifyDataSetChanged();   // Update event array adapter
                Log.d("EventListFragment", "Fetched " + eventList.size() + " events");
            }

            @Override
            public void onError(Exception e) {
            }

        });
    }
}
