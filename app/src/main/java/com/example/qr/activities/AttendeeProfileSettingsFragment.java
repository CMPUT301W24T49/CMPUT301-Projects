package com.example.qr.activities;

import static com.example.qr.utils.FirebaseUtil.uploadImageAndGetUrl;

import android.app.AlertDialog;
import android.net.Uri;

import java.net.URI;
import   java.net.URL;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.qr.R;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;

import org.json.JSONException;

import java.util.List;
import java.util.UUID;

/**
 * AttendeeProfileSettingsFragment displays the profile settings for an attendee user, allowing them to edit their profile information and upload a profile picture.
 */
public class AttendeeProfileSettingsFragment extends DialogFragment {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText homePageEditText;
    private Button editButton;
    private Button saveButton;
    private User currentUser;
    private RelativeLayout profileSettingsLayout;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;


    /**
     *
     * Attendee profile settings fragment
     *
     * @return public
     */
    public AttendeeProfileSettingsFragment() {

        // Required empty public constructor
    }

    @Override

/**
 *
 * On create
 *
 * @param savedInstanceState  the saved instance state.
 */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Initialize the ActivityResultLauncher for photo picking
        pickMedia = registerForActivityResult(new PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the photo picker
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                ImageView profileImageView = requireView().findViewById(R.id.profileImageView);
                profileImageView.setImageURI(uri);
                profileImageView.setTag(uri);

            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
    }

    @Override

/**
 *
 * On create view
 *
 * @param inflater  the inflater.
 * @param container  the container.
 * @param savedInstanceState  the saved instance state.
 * @return View
 */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendee_profile_settings, container, false);

        profileSettingsLayout = view.findViewById(R.id.profile_settings_layout);

        // Initialize the EditText fields and buttons
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        editButton = view.findViewById(R.id.editButton);
        saveButton = view.findViewById(R.id.saveButton);

        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        Button uploadButton = view.findViewById(R.id.uploadButton);
        Button removeButton = view.findViewById(R.id.removeButton);
        Button btnClose = view.findViewById(R.id.btn_close);
        Spinner homePageSpinner = view.findViewById(R.id.homePageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.homepage_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homePageSpinner.setAdapter(adapter);

        // Make the EditText fields read-only by default
        setEditTextEnabled(false);

        // Get the Android ID of the device
        String androidId = android.provider.Settings.Secure.getString(getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        // Fetch the user data from Firebase
        FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
            @Override

/**
 *
 * On collection fetched
 *
 * @param userList  the user list.
 */
            public void onCollectionFetched(List<User> userList) {

                for (User user : userList) {
                    if (user.getId().equals(androidId)) {
                        // Populate the EditText fields with the user data
                        firstNameEditText.setText(user.getFirstName());
                        lastNameEditText.setText(user.getLastName());
                        emailEditText.setText(user.getEmail());
                        phoneEditText.setText(user.getPhoneNumber());

                        String homepage = user.getHomepage();
                        if (homepage.equals("organizer")) {
                            homePageSpinner.setSelection(1);
                        } else if (homepage.equals("attendee")) {
                            homePageSpinner.setSelection(2);
                        } else {
                            homePageSpinner.setSelection(0);
                        }

                        currentUser = user;
                        // Load the profile picture
                        if(user.getProfilePicture().equals("")) {

                            String profilePictureUrl = "https://github.com/identicons/" + (currentUser.getFirstName().toLowerCase()).replace(" ", "") + ".png";
                            currentUser.setProfilePicture(profilePictureUrl);
                        }

                        Glide.with(getContext()).load(user.getProfilePicture()).into(profileImageView);

                        profileImageView.setTag(1);

                        // Save the current user
                        currentUser = user;

                        profileSettingsLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override

            /**
             *
             * On error
             *
             * @param e  the e.
             */
            public void onError(Exception e) {

                Log.e("AttendeeProfileSettings", "Error fetching user collection", e);
            }
        });

        // Add a click listener to the Edit button
        editButton.setOnClickListener(v -> {
            // Enable the EditText fields
            setEditTextEnabled(true);
        });

        // Add a click listener to the Save button
        saveButton.setOnClickListener(v -> {
            // Update the user data
            currentUser.setFirstName(firstNameEditText.getText().toString());
            currentUser.setLastName(lastNameEditText.getText().toString());
            currentUser.setName(firstNameEditText.getText().toString() + " " + lastNameEditText.getText().toString());
            currentUser.setEmail(emailEditText.getText().toString());
            currentUser.setPhoneNumber(phoneEditText.getText().toString());

            String selectedHomepage = homePageSpinner.getSelectedItem().toString();
            currentUser.setHomepage(selectedHomepage);

            Object returnValue = profileImageView.getTag();

            if(returnValue instanceof Uri) {
                // Upload the profile picture to Firebase Storage
                Uri profilePicture = (Uri) returnValue;
                uploadImageAndGetUrl(profilePicture, UUID.randomUUID().toString(), downloadUrl -> {
                    currentUser.setProfilePicture(downloadUrl.toString());
                    FirebaseUtil.updateUser(currentUser, aVoid -> {
                        // Disable the EditText fields
                        setEditTextEnabled(false);
                        // Show a success message
                        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();


                    }, e -> {
                        Log.e("AttendeeProfileSettings", "Error updating user", e);
                    });
                }, e -> {
                    Log.e("AttendeeProfileSettings", "Error uploading profile picture", e);
                });
            } else if ((Integer) returnValue == 1 ) {

                FirebaseUtil.updateUser(currentUser, aVoid -> {
                    // Disable the EditText fields
                    setEditTextEnabled(false);
                    // Show a success message
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();


                }, e -> {
                    Log.e("AttendeeProfileSettings", "Error updating user", e);
                });


            }
            else {
                currentUser.setProfilePicture("https://github.com/identicons/" + (currentUser.getFirstName().toLowerCase()).replace(" ", "") + ".png");
                Glide.with(getContext()).load(currentUser.getProfilePicture()).into(profileImageView);

                FirebaseUtil.updateUser(currentUser, aVoid -> {
                    // Disable the EditText fields
                    setEditTextEnabled(false);
                    // Show a success message
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();


                }, e -> {
                    Log.e("AttendeeProfileSettings", "Error updating user", e);
                });
            }

        });

        // Add a click listener to the Upload button
        uploadButton.setOnClickListener(v -> {
            // Launch the photo picker
            pickMedia.launch(new PickVisualMediaRequest());
        });

        // Add a click listener to the Remove button
        removeButton.setOnClickListener(v -> {
            // Create a confirmation dialog
            new AlertDialog.Builder(getContext())
                    .setTitle("Remove Profile Picture")
                    .setMessage("Are you sure you want to remove your profile picture?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // If the user confirms, remove the profile picture
                        String profilePictureUrl = "https://github.com/identicons/" + (currentUser.getFirstName().toLowerCase()).replace(" ", "") + ".png";
                        currentUser.setProfilePicture(profilePictureUrl);
                        Glide.with(getContext()).load(currentUser.getProfilePicture()).into(profileImageView);
                        profileImageView.setTag(1);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


    /**
     *
     * Sets the edit text enabled
     *
     * @param enabled  the enabled.
     */
    private void setEditTextEnabled(boolean enabled) {

        firstNameEditText.setEnabled(enabled);
        lastNameEditText.setEnabled(enabled);
        emailEditText.setEnabled(enabled);
        phoneEditText.setEnabled(enabled);
    }
}
