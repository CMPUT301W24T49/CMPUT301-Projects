package com.example.qr.activities;

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
import com.example.qr.R;

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

        // Set up the button to open the image picker
        Button uploadProfilePicture = view.findViewById(R.id.uploadButton);
        uploadProfilePicture.setOnClickListener(v -> {
            Log.d("ProfileSettings", "Upload button clicked");
            // Launch the photo picker to let the user choose only images
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        return view;
    }
}
