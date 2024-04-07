package com.example.qr.models;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import java.util.ArrayList;
import java.util.List;

public class ProfileAndPosterAdapter extends ArrayAdapter<Object> {

    private ArrayList<Object> items;
    private Context context;

    /**
     * Constructs an ProfileAndPosterAdapter.
     *
     * @param context The current context, used to inflate the layout file.
     * @param items An ArrayList of User or Event objects to be displayed.
     */
    public ProfileAndPosterAdapter(Context context, ArrayList<Object> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    /**
     * Provides a view for an AdapterView.
     *
     * @param position The position in the list of data that should be displayed in the list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_image_list_view_info, parent, false);
        }
        Object item = items.get(position);
        ImageView imageView = view.findViewById(R.id.image_list_info);
        TextView textView = view.findViewById(R.id.image_picture);
        if (item instanceof User) {
            User user = (User) item;
            String profilePictureUrl = user.getProfilePicture();
            String userName = user.getName();
            if (profilePictureUrl != null && !profilePictureUrl.trim().isEmpty() &&
                    userName != null && !userName.trim().isEmpty()) {
                textView.setText(userName);
            }
        } else if (item instanceof Event) {
            Event event = (Event) item;
            String eventPosterUrl = event.getEventPoster();
            String eventTitle = event.getTitle();
            if (eventPosterUrl != null && !eventPosterUrl.trim().isEmpty() &&
                    eventTitle != null && !eventTitle.trim().isEmpty()) {
                textView.setText(eventTitle);
            }
        }

        return view;
    }
    public void updateItems(List<Object> newItems) {
        clear();
        addAll(newItems);
        notifyDataSetChanged();
    }
}
