//package com.example.qr.activities;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.qr.R;
//
//public class ImageListFragment extends Fragment {
//
//    public ImageListFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_image_list, container, false);
//
//        ListView listView = view.findViewById(R.id.listview_images);
//        Button btnClose = view.findViewById(R.id.btn_close_image_list);
//
//        // TODO: Replace with actual image data
//        String[] imageNames = {"Image 1", "Image 2", "Image 3", "Image 4", "Image 5"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                imageNames
//        );
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
//            // TODO: Show image detail dialog or fragment
//            // You can pass the selected image info to the detail fragment or dialog
//        });
//
//        btnClose.setOnClickListener(v -> {
//            if (isAdded() && getActivity() != null) {
//                getActivity().onBackPressed();
//            }
//        });
//
//        return view;
//    }
//
//    // TODO: Implement the adapter for the ListView to display the images
//}
package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Image;
import com.example.qr.models.ImageArrayAdapter;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * ImageListFragment displays a list of images and provides functionality to view or edit them in detail.
 */
public class ImageListFragment extends Fragment {

    ListView imageList;
    ArrayList<Image> imageDataList;
    ImageArrayAdapter imageArrayAdapter;

    private int positionToEdit;
    private FirebaseFirestore db;

    public ImageListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list, container, false);

        ListView listView = view.findViewById(R.id.listview_images);
        Button btnClose = view.findViewById(R.id.btn_close_image_list);


        imageDataList = new ArrayList<>();
        imageArrayAdapter = new ImageArrayAdapter(getContext(), imageDataList);
        listView.setAdapter(imageArrayAdapter);

        fetchData();

//        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
//            // Handle list item click
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;
                Image clickedImage = (Image) adapterView.getAdapter().getItem(position);
                ImageDetailDialogFragment addCityFragment = ImageDetailDialogFragment.newInstance(String.valueOf(clickedImage));
                addCityFragment.show(getParentFragmentManager(), "Image Detail");
            }
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void fetchData() {
        FirebaseUtil.fetchCollection("Images", Image.class, new FirebaseUtil.OnCollectionFetchedListener<Image>() {
            @Override
            public void onCollectionFetched(List<Image> ImageList) {
                // Handle the fetched Image here
                imageDataList.addAll(ImageList);
                imageArrayAdapter.notifyDataSetChanged();
                Log.d("ImageListFragment", "Fetched " + ImageList.size() + " images");
            }

            @Override
            public void onError(Exception e) {
            }


        });
    }
}
