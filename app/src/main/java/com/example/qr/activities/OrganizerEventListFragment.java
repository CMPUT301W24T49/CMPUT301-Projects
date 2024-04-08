package com.example.qr.activities;

import static com.example.qr.activities.MainActivity.androidId;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * OrganizerEventListFragment displays a list of events organized by the user.
 */
public class OrganizerEventListFragment extends Fragment {

    ArrayList<Event> eventDataList;
    EventArrayAdapter eventArrayAdapter;

    RelativeLayout fragmentLayout;



    /**
     *
     * Organizer event list fragment
     *
     * @return public
     */
    public OrganizerEventListFragment() {

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
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        fragmentLayout = view.findViewById(R.id.fragment_event_list_layout);

        // Button initialization
        ListView listView = view.findViewById(R.id.listview_events);
        Button btnClose = view.findViewById(R.id.btn_close_event_list);

        // Initialize event list and event array adapter
        eventDataList = new ArrayList<>();
        eventArrayAdapter = new EventArrayAdapter(getContext(), eventDataList);
        listView.setAdapter(eventArrayAdapter);

        fetchData();

        // Clicking an event directs to it's detail page
        listView.setOnItemClickListener((adapterView, view1, position, rowId) -> {
            // Get position of clicked event
            Event event = eventDataList.get(position);

            // Send event to detail page (organizer)
            // Adapted from answer given by JoÃ£o Marcos
            // https://stackoverflow.com/questions/24555417/how-to-send-data-from-one-fragment-to-another-fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("Event", event);   // Store event data in a bundle (key-value pair)

            OrganizerEventDetailFragment organizerEventDetails = new OrganizerEventDetailFragment();
            organizerEventDetails.setArguments(bundle); // Pass event data to event detail page

            // Navigate to event detail page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerEventDetails)
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

        fragmentLayout.setVisibility(View.GONE);    // Hide loading screen
        return view;
    }

    // Fetch events from Firebase and add them to eventList

    /**
     *
     * Fetch data
     *
     */
    private void fetchData() {

        // Citation: OpenAI, ChatGPT 4, 2024
        // Prompt: How would i use fetchCollection to fetch event data?
        FirebaseUtil.fetchCollection("Events", Event.class, new FirebaseUtil.OnCollectionFetchedListener<Event>() {
            @Override


/**
 *
 * On collection fetched
 *
 * @param eventList  the event list.
 */
            public void onCollectionFetched(List<Event> eventList) {    // Handle the fetched events
                // Only add the events which have the same organizerID
                for(Event event : eventList){

                    if(event.getOrganizerId().equals(androidId)){
                        eventDataList.add(event);
                    }
                }

                eventArrayAdapter.notifyDataSetChanged();   // Update event array adapter

                fragmentLayout.setVisibility(View.VISIBLE); // Show loading screen
                Log.d("EventListFragment", "Fetched " + eventList.size() + " events");
            }

            @Override

/**
 *
 * On error
 *
 * @param e  the e.
 */
            public void onError(Exception e) {

            }
            // End of citation

        });
    }
}
