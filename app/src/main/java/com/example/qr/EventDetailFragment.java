package com.example.qr;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EventDetailFragment extends DialogFragment {

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        Button btnDelete = view.findViewById(R.id.btn_delete_event);
        Button btnCancel = view.findViewById(R.id.btn_cancel_event);

        btnDelete.setOnClickListener(v -> {
            // TODO: Implement event deletion logic
            dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            // Close the dialog
            dismiss();
        });

        return view;

    }
}