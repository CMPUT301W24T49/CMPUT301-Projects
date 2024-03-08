package com.example.qr.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import com.example.qr.R;

import java.util.Objects;

public class AttendeeProfileSettingsFragment extends DialogFragment {

    private static final int PICK_IMAGE = 1;
    private ActivityResultLauncher<String> mGetContent;

    public AttendeeProfileSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the ActivityResultLauncher
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // Handle the returned Uri, for example, set it to an ImageView
            if (uri != null) {
                ImageView profileImageView = requireView().findViewById(R.id.profileImageView);
                profileImageView.setImageURI(uri);
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
            // Check if mGetContent is not null
            if (mGetContent != null) {
                // Launch the image picker
                mGetContent.launch("image/*");
            } else {
                // If mGetContent is null, use an explicit intent to open the image picker
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        return view;
    }
}
