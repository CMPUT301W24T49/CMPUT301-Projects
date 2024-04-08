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

/**
 * An ArrayAdapter for displaying images in a list.
 * Manages a list of user profile & event poster and populates a ListView with views representing each image.
 */
public class ProfileAndPosterAdapter extends ArrayAdapter<Object> {

    private ArrayList<Object> items;
    private Context context;

    public ProfileAndPosterAdapter(Context context, ArrayList<Object> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }
    
    // citation: OpenAI, ChatGPT 4, 2024: How to handle object of two different data type using a single Array Adapter
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
