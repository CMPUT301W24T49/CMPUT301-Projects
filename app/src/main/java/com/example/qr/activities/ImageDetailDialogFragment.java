package com.example.qr.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.qr.R;


public class ImageDetailDialogFragment extends DialogFragment {

    // Assume you pass the image details as arguments
    public static ImageDetailDialogFragment newInstance(String imageUrl) {
        ImageDetailDialogFragment fragment = new ImageDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("image_url", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image_detail, null);

        ImageView imageView = view.findViewById(R.id.image_detail);
        Button btnDelete = view.findViewById(R.id.btn_delete_image);
        Button btnCancel = view.findViewById(R.id.btn_cancel_image);

        // TODO: Set the image for imageView, for example using Glide or Picasso
        String imageUrl = getArguments().getString("image_url");
        // Glide.with(this).load(imageUrl).into(imageView);

        btnDelete.setOnClickListener(v -> {
            // TODO: Implement your delete logic
            dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            // Close the dialog
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}