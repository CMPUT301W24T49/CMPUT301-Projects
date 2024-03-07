package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;

public class ImageListFragment extends Fragment {

    public ImageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list, container, false);

        ListView listView = view.findViewById(R.id.listview_images);
        Button btnClose = view.findViewById(R.id.btn_close_image_list);

        // TODO: Replace with actual image data
        String[] imageNames = {"Image 1", "Image 2", "Image 3", "Image 4", "Image 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                imageNames
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            // TODO: Show image detail dialog or fragment
            // You can pass the selected image info to the detail fragment or dialog
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    // TODO: Implement the adapter for the ListView to display the images
}
