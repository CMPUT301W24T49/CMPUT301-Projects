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

//public class AttendeesArrayAdapter extends ArrayAdapter<Attendee> {
//
//    private ArrayList<Attendee> attendees;
//    private Context context;
//
//    public AttendeesArrayAdapter(Context context, ArrayList<Attendee> attendees) {
//        super(context, 0, attendees);
//        this.context = context;
//        this.attendees = attendees;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = convertView;
//
//        if (convertView == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.fragment_attendee_list, parent, false);
//        }
//
//        Attendee attendee = attendees.get(position);
//        TextView attendeeName = convertView.findViewById(R.id.attendee_name_textview);
//        attendeeName.setText(attendee.getName());
//
//        return view;
//    }
//
//}
