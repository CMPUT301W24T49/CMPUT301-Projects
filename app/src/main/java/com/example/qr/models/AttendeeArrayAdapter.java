// Adapted from Cole Haraga's EventArrayAdapter.java and lab 5 code

package com.example.qr.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr.R;

import java.util.ArrayList;

/**
 * An ArrayAdapter for displaying a list of attendees' names.
 * Converts an ArrayList of attendees into View items loaded into the ListView container.
 */
public class AttendeeArrayAdapter extends ArrayAdapter<User> {

    private ArrayList<User> attendeeDataList; // List of attendees
    private Context context;    // Current context

    /**
     * Constructs a new AttendeeArrayAdapter.
     *
     * @param context The current context which is used to inflate the layout file.
     * @param attendees An ArrayList of attendee names to display in the list.
     */
    public AttendeeArrayAdapter(Context context, ArrayList<User> attendees) {
        super(context, 0, attendees);
        this.context = context;
        this.attendeeDataList = attendees;
    }
    
    /**
     * Provides a view for an AdapterView.
     *
     * @param position The position in the list of data that should be displayed in the list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_attendee_list_info, parent, false);
        }

        User attendee = attendeeDataList.get(position);
        TextView attendeeName = view.findViewById(R.id.attendee_name);
        TextView attendeeUserId = view.findViewById(R.id.attendee_user_id);
        attendeeName.setText(attendee.getName());
        attendeeUserId.setText(attendee.getId());

        return view;
    }

}
