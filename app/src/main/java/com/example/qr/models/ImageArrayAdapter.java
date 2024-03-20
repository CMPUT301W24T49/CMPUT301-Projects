package com.example.qr.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * An ArrayAdapter for displaying images in a list.
 * Manages a list of Image objects and populates a ListView with views representing each image.
 */
public class ImageArrayAdapter extends ArrayAdapter<Image> {

    private ArrayList<Image> images;
    private Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructs an ImageArrayAdapter.
     *
     * @param context The current context, used to inflate the layout file.
     * @param profile An ArrayList of Image objects to be displayed.
     */
    public ImageArrayAdapter(Context context, ArrayList<Image> profile) {
        super(context, 0);
        this.context = context;
        this.images = profile;
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
            view = LayoutInflater.from(context).inflate(R.layout.fragment_profile_list_view_info, parent, false);
        }

        Image image = images.get(position);
//        TextView imageID = view.findViewById(R.id.image_list_info);
//        imageID.setText(image.getId());
        TextView textTitle = view.findViewById(R.id.image_id);
        ImageView imageDetailView = view.findViewById(R.id.image_detail);
        TextView imageUploadedBY = view.findViewById(R.id.image_uploadedBy);
        TextView imageUploadTime = view.findViewById(R.id.image_uploadTime);
        TextView imageRelatedTo = view.findViewById(R.id.image_relatedTo);
        if (textTitle == null) {
            Log.e("ImageArrayAdapter", "textTitle is null!");
        } else {
            textTitle.setText(image.getId());
        }
        if (imageDetailView == null) {
            Log.e("ImageArrayAdapter", "imageDetailView is null!");
        } else {
            imageDetailView.setImageURI(Uri.parse(image.getUrl()));
        }
        if (imageUploadedBY == null) {
            Log.e("ImageArrayAdapter", "imageUploadedBY is null!");
        } else {
            imageUploadedBY.setText(image.getUploadedBy());
        }
        if (imageUploadTime == null) {
            Log.e("ImageArrayAdapter", "imageUploadTime is null!");
        } else {
            imageUploadTime.setText(dateFormat.format(image.getUploadTime()));
        }
        if (imageRelatedTo == null) {
            Log.e("ImageArrayAdapter", "imageRelatedTo is null!");
        } else {
            imageRelatedTo.setText(image.getRelatedTo());
        }

        return view;
    }
}
