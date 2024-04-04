package com.example.qr.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.qr.R;

import org.json.JSONException;

public class AttendeeProfileSettingsFragment extends DialogFragment {

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    public AttendeeProfileSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the ActivityResultLauncher for photo picking
        pickMedia = registerForActivityResult(new PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the photo picker
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                ImageView profileImageView = requireView().findViewById(R.id.profileImageView);
                profileImageView.setImageURI(uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendee_profile_settings, container, false);


        Button uploadProfilePicture = view.findViewById(R.id.uploadButton);
        Button removeProfilePicture = view.findViewById(R.id.removeButton);
        //Button generateButton = view.findViewById(R.id.generateButton);

        uploadProfilePicture.setOnClickListener(v -> {
            Log.d("ProfileSettings", "Upload button clicked");
            // Launch the photo picker to let the user choose only images
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        removeProfilePicture.setOnClickListener(v -> {
            Log.d("ProfileSettings", "Remove button clicked");

            new AlertDialog.Builder(getContext())
                    .setTitle("Remove Profile Picture")
                    .setMessage("Are you sure you want to remove the profile picture?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // User clicked YES button
                        ImageView profileImageView = requireView().findViewById(R.id.profileImageView);
                        profileImageView.setImageResource(R.drawable.default_pfp);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });


//        /*
//            // github, August 14, 2013, github.blog, by Jason Long,
//            // source URL: https://github.blog/2013-08-14-identicons/
//            // Usage: this blog post explains how to generate Identicons
//        */
//        generateButton.setOnClickListener(v -> {
//            Log.d("ProfileSettings", "Generate button clicked");
//            // Assume the username is always "john"
//            String username = "basharharash";
//            // Use the username to generate a URL to an Identicon
//            String url = "https://github.com/identicons/" + username + ".png";
//            // Load the Identicon into the ImageView
//            ImageView profileImageView = requireView().findViewById(R.id.profileImageView);
//            Glide.with(this).load(url).into(profileImageView);
//        });

        return view;
    }
}
