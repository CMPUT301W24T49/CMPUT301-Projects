package com.example.qr.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.qr.R;
import com.example.qr.models.User;

import java.io.Serializable;
/**
 * AdminUserProfileDetailFragment presents detailed information about a user profile, with options for administrative actions such as deletion.
 */

public class AdminUserProfileDetailFragment extends DialogFragment {
    interface UserDetailDialogListener {
        void onDeleteUser(String userId);
    }

    private UserDetailDialogListener listener;
    private User user;

    static AdminUserProfileDetailFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", (Serializable) user);
        AdminUserProfileDetailFragment fragment = new AdminUserProfileDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserDetailDialogListener) {
            listener = (UserDetailDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UserDetailDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        user = (User) getArguments().getSerializable("user");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_admin_user_profile_detail, null);

//        TextView textTitle = view.findViewById(R.id.textview_user_profile_detail);
        TextView userNameTextView = view.findViewById(R.id.userNameTextView); // Assuming you have added this
        TextView userRoleTextView = view.findViewById(R.id.userRoleTextView); // And so on for the other details
        TextView userEmailTextView = view.findViewById(R.id.userEmailTextView);
        TextView userPhoneTextView = view.findViewById(R.id.userPhoneTextView);
        TextView userHomepageTextView = view.findViewById(R.id.userHomepageTextView);

//        textTitle.setText(user.getName());
        userNameTextView.setText(user.getName());
        userRoleTextView.setText(user.getRole());
        userEmailTextView.setText(user.getContactInfo().get("email")); // Assuming the map has an "email" key
        userPhoneTextView.setText(user.getContactInfo().get("phone")); // Assuming the map has a "phone" key
        userHomepageTextView.setText(user.getHomepage());

        // ... Initialize other TextViews for details as needed ...

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Profile Details")
                .setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .setPositiveButton("Delete", (dialog, which) -> listener.onDeleteUser(user.getId()))
                .create();
    }
}
