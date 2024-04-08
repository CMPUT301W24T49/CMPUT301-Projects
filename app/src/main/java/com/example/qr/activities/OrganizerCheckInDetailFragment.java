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
import com.example.qr.models.User;

import java.util.Map;
import java.util.List;

/**
 * OrganizerCheckInDetailFragment displays detailed information about a user's check-ins.
 */
public class OrganizerCheckInDetailFragment extends DialogFragment {

    User user;

    Map<String, Integer> userCheckInCount;     // Count of each user's check-ins

    @NonNull
    @Override

/**
 *
 * On create dialog
 *
 * @param savedInstanceState  the saved instance state.  It is Nullable
 * @return Dialog
 */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_organizer_checkin_detail, null);

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
            userCheckInCount = (Map<String, Integer>) getArguments().getSerializable("Map");
        }

        TextView userId = view.findViewById(R.id.user_id);
        TextView checkInCount = view.findViewById(R.id.check_in_count);


        if (userCheckInCount != null) {
            checkInCount.setText(String.valueOf(userCheckInCount.get(user.getId())));
        }
        userId.setText(user.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setView(view)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {  // Close dialog
                    @Override

/**
 *
 * On click
 *
 * @param dialog  the dialog.
 * @param which  the which.
 */
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();
    }


}
