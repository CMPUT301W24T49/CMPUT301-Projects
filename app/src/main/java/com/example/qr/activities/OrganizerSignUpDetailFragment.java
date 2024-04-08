package com.example.qr.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.qr.R;
import com.example.qr.models.SignUp;
import com.example.qr.models.User;

import java.util.Map;
import java.util.List;

public class OrganizerSignUpDetailFragment extends DialogFragment {

    User user;
    Map<String, SignUp> userSignUpData;  // Sign-up data for each user

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_organizer_checkin_detail, null);

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
        }
        userSignUpData = (Map<String, SignUp>) getArguments().getSerializable("CheckIns");


        TextView userId = view.findViewById(R.id.user_id);
        TextView timestamp = view.findViewById(R.id.check_in_timestamp);

        userId.setText(user.getId());
        timestamp.setText(userSignUpData.getSignUpTime());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setView(view)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {  // Close dialog
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }


}
