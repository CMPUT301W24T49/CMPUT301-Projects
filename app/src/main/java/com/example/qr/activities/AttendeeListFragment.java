package com.example.qr.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AttendeeListFragment extends Fragment {

    public AttendeeListFragment() {
        // Required empty public constructor
    }

    private RecyclerView attendeesRecyclerView;
    private AttendeesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendee_list, container, false);
        attendeesRecyclerView = view.findViewById(R.id.attendees_recycler_view);
        attendeesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadAttendees();
        return view;
    }


    private void loadAttendees() {
        FirebaseUtil.getEventAttendees(eventId, new FirestoreCallback<List<Attendee>>() {
            @Override
            public void onCallback(List<Attendee> attendees) {
                adapter = new AttendeesAdapter(attendees, getContext());
                attendeesRecyclerView.setAdapter(adapter);
            }
        });
    }







}
