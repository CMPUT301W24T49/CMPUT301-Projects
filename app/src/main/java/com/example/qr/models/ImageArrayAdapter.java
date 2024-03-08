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

public class ImageArrayAdapter extends ArrayAdapter<Image> {

    private ArrayList<Image> images;
    private Context context;

    public ImageArrayAdapter(Context context, ArrayList<Image> profile) {
        super(context, 0);
        this.context = context;
        this.images = profile;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_profile_list_view_info, parent, false);
        }

        Image image = images.get(position);
        TextView imageID = view.findViewById(R.id.image_list_info);
        imageID.setText(image.getId());

        return view;
    }
}
