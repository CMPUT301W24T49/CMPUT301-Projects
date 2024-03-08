
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.fragment.app.DialogFragment;
//
//import com.example.qr.R;
//
//public class EventDetailFragment extends DialogFragment {
//
//    public EventDetailFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
//
//        Button btnDelete = view.findViewById(R.id.btn_delete_event);
//        Button btnCancel = view.findViewById(R.id.btn_cancel_event);
//
//        btnDelete.setOnClickListener(v -> {
//            // TODO: Implement event deletion logic
//            dismiss();
//        });
//
//        btnCancel.setOnClickListener(v -> {
//            // Close the dialog
//            dismiss();
//        });
//
//        return view;
//
//    }
//}

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
import com.example.qr.models.Event;

import java.io.Serializable;

public class EventDetailFragment extends DialogFragment {
    interface EventDetailDialogListener {
        void onDeleteEvent(Event event);
    }

    private EventDetailDialogListener listener;
    private Event event;

    static EventDetailFragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putSerializable("event", (Serializable) event);
        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EventDetailDialogListener) {
            listener = (EventDetailDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EventDetailDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        event = (Event) getArguments().getSerializable("event");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_detail, null);

        TextView textTitle = view.findViewById(R.id.textview_event_detail);
        textTitle.setText(event.getTitle());


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Event Details")
                .setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .setPositiveButton("Delete", (dialog, which) -> listener.onDeleteEvent(event))
                .create();
    }
}
