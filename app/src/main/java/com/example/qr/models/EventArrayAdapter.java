package com.example.qr.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr.R;

import java.util.ArrayList;

public class EventArrayAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> events;
    private Context context;

    public EventArrayAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_event_list_view_info, parent, false);
        }

        Event event = events.get(position);
        TextView eventName = view.findViewById(R.id.event_list_info);
        eventName.setText(event.getTitle());

        return view;
    }
}
