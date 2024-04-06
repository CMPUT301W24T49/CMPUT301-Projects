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

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.Image;
import com.example.qr.models.ImageArrayAdapter;
import com.example.qr.models.ProfileAndPosterAdapter;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
/**
 * ImageListFragment displays a list of images and provides functionality to view or edit them in detail.
 */
public class ImageListFragment extends Fragment {

    ListView imageList;
    ArrayList<Object> imageDataList;
    ProfileAndPosterAdapter profileAndPosterAdapter;

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
//        Button btnClose = view.findViewById(R.id.btn_close_image_list);


        imageDataList = new ArrayList<>();
        profileAndPosterAdapter = new ProfileAndPosterAdapter(getContext(), imageDataList);
        listView.setAdapter(profileAndPosterAdapter);

        fetchUserData();
        fetchEventData();
//        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
//            // Handle list item click
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;

                Object clickedItem = adapterView.getAdapter().getItem(position);
                ImageDetailDialogFragment objectDetailDialog;
                if (clickedItem instanceof User) {
                    User clickedUser = (User) clickedItem;
                    objectDetailDialog = ImageDetailDialogFragment.newInstance(clickedUser.getProfilePicture(), clickedUser.getName(), clickedUser.getId());
                    objectDetailDialog.show(getParentFragmentManager(), "Image Detail");
                } else if (clickedItem instanceof Event) {
                    Event clickedEvent = (Event) clickedItem;
                    objectDetailDialog = ImageDetailDialogFragment.newInstance(clickedEvent.getEventPoster(), clickedEvent.getTitle(), clickedEvent.getId());
                    objectDetailDialog.show(getParentFragmentManager(), "Event Detail");
                } else {
                    Toast.makeText(getContext(), "Item type not recognized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object imageToBeDeleted = imageDataList.get(position);
                if (imageToBeDeleted instanceof User) {
                    User clickedUser = (User) imageToBeDeleted;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete Image") // Set the title
                            .setMessage("Are you sure you want to delete this image?") // Set the message
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                // Delete the image if the user confirms
                                FirebaseUtil.updateImage("Users", clickedUser.getId(), "profilePicture", "https://firebasestorage.googleapis.com/v0/b/cmput301-b4a16.appspot.com/o/PV.jpeg?alt=media&token=df642ee7-65df-451d-ae3b-359302fb0dc1",
                                        aVoid -> {
                                            imageDataList.remove(position);
                                            profileAndPosterAdapter.notifyDataSetChanged();
                                            fetchUserData();
                                            fetchEventData();
                                            //                                        Toast.makeText(getActivity(), "Image " + imageToBeDeleted.getId() + " deleted successfully", Toast.LENGTH_SHORT).show();
                                        },
                                        e -> {
                                            Toast.makeText(getActivity(), "Failed to delete image", Toast.LENGTH_SHORT).show();
                                        });
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else if (imageToBeDeleted instanceof Event) {
                    Event clickedEvent = (Event) imageToBeDeleted;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete Image") // Set the title
                            .setMessage("Are you sure you want to delete this image?") // Set the message
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                // Delete the image if the user confirms
                                FirebaseUtil.updateImage("Events", clickedEvent.getId(), "eventPoster", "https://firebasestorage.googleapis.com/v0/b/cmput301-b4a16.appspot.com/o/PV.jpeg?alt=media&token=df642ee7-65df-451d-ae3b-359302fb0dc1",
                                        aVoid -> {
                                            imageDataList.remove(position);
                                            profileAndPosterAdapter.notifyDataSetChanged();
                                            fetchUserData();
                                            fetchEventData();
                                            //                                        Toast.makeText(getActivity(), "Image " + imageToBeDeleted.getId() + " deleted successfully", Toast.LENGTH_SHORT).show();
                                        },
                                        e -> {
                                            Toast.makeText(getActivity(), "Failed to delete image", Toast.LENGTH_SHORT).show();
                                        });
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                return true; // Indicate that the click was handled
            }
        });


//        btnClose.setOnClickListener(v -> {
//            if (isAdded() && getActivity() != null) {
//                getActivity().onBackPressed();
//            }
//        });

        return view;
    }

    private void fetchUserData() {
        FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
            @Override
            public void onCollectionFetched(List<User> UserList) {
                // Handle the fetched Image here
                imageDataList.addAll(UserList);
                profileAndPosterAdapter.notifyDataSetChanged();
//                Log.d("ImageListFragment", "Fetched " + ImageList.size() + " images");
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
    private void fetchEventData() {
        FirebaseUtil.fetchCollection("Events", Event.class, new FirebaseUtil.OnCollectionFetchedListener<Event>() {
            @Override
            public void onCollectionFetched(List<Event> EventList) {
                // Handle the fetched Image here
                imageDataList.addAll(EventList);
                profileAndPosterAdapter.notifyDataSetChanged();
//                Log.d("ImageListFragment", "Fetched " + ImageList.size() + " images");
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
