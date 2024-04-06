package com.example.qr.models;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import java.util.ArrayList;

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
            if (profilePictureUrl != null && !profilePictureUrl.equals("") && user.getName() != null && !profilePictureUrl.equals(" ")) {
//                Glide.with(context)
//                        .load(user.getProfilePicture())
//                        .into(imageView);
                textView.setText(user.getName());
            }
        } else if (item instanceof Event) {
            Event event = (Event) item;
            String eventPosterUrl = event.getEventPoster();
            if (eventPosterUrl != null && !eventPosterUrl.equals("") && !eventPosterUrl.equals(" ")&& event.getTitle() != null) {
//                Glide.with(context)
//                        .load(event.getEventPoster())
//                        .into(imageView);
                textView.setText(event.getTitle());
            }
        }
        return view;
    }
}
