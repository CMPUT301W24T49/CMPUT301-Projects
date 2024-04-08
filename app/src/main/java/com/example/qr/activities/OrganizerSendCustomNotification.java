package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.Notification;
import com.example.qr.utils.FirebaseUtil;

import java.util.Date;

public class OrganizerSendCustomNotification extends Fragment {
    Event event;
    public OrganizerSendCustomNotification() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_custom_notification, container, false);
        event = (Event) getArguments().getSerializable("event");
        EditText editText = view.findViewById(R.id.custom_msg);
        Button sendNotifiton = view.findViewById(R.id.btnNotification);
        Button btnClose = view.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> {
            // Check if fragment is added to an activity and if activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        sendNotifiton.setOnClickListener(v ->{

            String message = editText.getText().toString();
            Notification notification = new Notification("notification" + System.currentTimeMillis(), event.getId(), message, new Date(), false);
            FirebaseUtil.addNotification(notification, aVoid -> {}, e -> {});
            editText.setText("");
        });
        return view;
    }

}
