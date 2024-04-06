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

public class EventSpinnerAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> events;
    private Context context;

    public EventSpinnerAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.fragment_spinner_item, events); // Make sure you have a layout file named spinner_item.xml for the selected item view
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_spinner_dropdown_item, parent, false); // Make sure you have a layout file named spinner_dropdown_item.xml for the dropdown view
        }

        TextView tvEventId = convertView.findViewById(R.id.tvEventId);
        TextView tvEventName = convertView.findViewById(R.id.tvEventName);

        Event currentEvent = events.get(position);
        tvEventId.setText(currentEvent.getId());
        tvEventName.setText(currentEvent.getTitle());

        return convertView;
    }
}
