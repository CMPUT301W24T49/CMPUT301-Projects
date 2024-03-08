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

public class UserArrayAdapter extends ArrayAdapter<Event> {

    private ArrayList<User> profile;
    private Context context;

    public UserArrayAdapter(Context context, ArrayList<User> profile) {
        super(context, 0);
        this.context = context;
        this.profile = profile;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_profile_list_view_info, parent, false);
        }

        User user = profile.get(position);
        TextView eventName = view.findViewById(R.id.profile_list_info);
        eventName.setText(user.getName());

        return view;
    }
}
