package com.example.qr.models;

import android.content.Context;
import android.util.Log;
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
 * An ArrayAdapter for displaying user information in a list.
 * Creates views for each User object within a ListView.
 * Each item in the list represents a single user.
 */
public class UserArrayAdapter extends ArrayAdapter<Event> {

    private ArrayList<User> profile;
    private Context context;

    /**
     * Constructs a new UserArrayAdapter.
     *
     * @param context The current context which is used to inflate the layout file.
     * @param profile An ArrayList of User objects to be displayed.
     */
    public UserArrayAdapter(Context context, ArrayList<User> profile) {
        super(context, 0);
        this.context = context;
        this.profile = profile;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.).
     * This method inflates a custom layout for each User object and sets the user's name in a TextView.
     *
     * @param position The position in the list of data that should be displayed in the list item view.
     * @param convertView The recycled view to populate, or null if a new view needs to be created.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_profile_list_view_info, parent, false);
        }
        // Once user profile pic is implemented add pic as well
        User user = profile.get(position);
        TextView userId = view.findViewById(R.id.profile_list_info);
        TextView userName = view.findViewById(R.id.user_name);

        userId.setText(user.getId());
        userName.setText(user.getName());

        return view;
    }
}
