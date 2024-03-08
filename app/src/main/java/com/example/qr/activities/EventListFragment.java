package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * EventListFragment displays a list of events, allowing users to view detailed information or edit them.
 */
public class EventListFragment extends Fragment {

    ListView eventList;
    ArrayList<Event> eventDataList;
    EventArrayAdapter eventArrayAdapter;

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
        Button btnClose = view.findViewById(R.id.btn_close_event_list);


        eventDataList = new ArrayList<>();
        eventArrayAdapter = new EventArrayAdapter(getContext(), eventDataList);
        listView.setAdapter(eventArrayAdapter);

        fetchData();

//        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
//            // Handle list item click
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;
                Event clickedEvent = (Event) adapterView.getAdapter().getItem(position);
                EventDetailFragment addCityFragment = EventDetailFragment.newInstance(clickedEvent);
                addCityFragment.show(getParentFragmentManager(), "Event Detail");
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
