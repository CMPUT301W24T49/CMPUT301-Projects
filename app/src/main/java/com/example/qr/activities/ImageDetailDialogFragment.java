//package com.example.qr.activities;
//
//import android.app.Dialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.DialogFragment;
//
//import com.example.qr.R;
//
//
//public class ImageDetailDialogFragment extends DialogFragment {
//
//    // Assume you pass the image details as arguments
//    public static ImageDetailDialogFragment newInstance(String imageUrl) {
//        ImageDetailDialogFragment fragment = new ImageDetailDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("image_url", imageUrl);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image_detail, null);
//
//        ImageView imageView = view.findViewById(R.id.image_detail);
//        Button btnDelete = view.findViewById(R.id.btn_delete_image);
//        Button btnCancel = view.findViewById(R.id.btn_cancel_image);
//
//        // TODO: Set the image for imageView, for example using Glide or Picasso
//        String imageUrl = getArguments().getString("image_url");
//        // Glide.with(this).load(imageUrl).into(imageView);
//
//        btnDelete.setOnClickListener(v -> {
//            // TODO: Implement your delete logic
//            dismiss();
//        });
//
//        btnCancel.setOnClickListener(v -> {
//            // Close the dialog
//            dismiss();
//        });
//
//        builder.setView(view);
//        return builder.create();
//    }
//}

package com.example.qr.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.qr.R;
import com.example.qr.models.Image;
import com.example.qr.models.User;


public class ImageDetailDialogFragment extends DialogFragment {
    interface ImageDetailDialogListener {
        void onDeleteImage(Image image);
    }
    private ImageDetailDialogListener listener;
    private Image image;
    // Assume you pass the image details as arguments
    public static ImageDetailDialogFragment newInstance(String imageUrl) {
        ImageDetailDialogFragment fragment = new ImageDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("image_url", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AdminUserProfileDetailFragment.UserDetailDialogListener) {
            listener = (ImageDetailDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ImageDetailDialogListener");
        }
    }
    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image_detail, null);
//
//        ImageView imageView = view.findViewById(R.id.image_detail);
//        Button btnDelete = view.findViewById(R.id.btn_delete_image);
//        Button btnCancel = view.findViewById(R.id.btn_cancel_image);
//
//        // TODO: Set the image for imageView, for example using Glide or Picasso
//        String imageUrl = getArguments().getString("image_url");
//        // Glide.with(this).load(imageUrl).into(imageView);
//
//        btnDelete.setOnClickListener(v -> {
//            // TODO: Implement your delete logic
//            dismiss();
//        });
//
//        btnCancel.setOnClickListener(v -> {
//            // Close the dialog
//            dismiss();
//        });
//
//        builder.setView(view);
//        return builder.create();
//    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        image = (Image) getArguments().getSerializable("image");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image_detail, null);

        TextView textTitle = view.findViewById(R.id.image_id);
        textTitle.setText(image.getId());

        // ... Initialize other TextViews for details as needed ...

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Profile Details")
                .setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .setPositiveButton("Delete", (dialog, which) -> listener.onDeleteImage(image))
                .create();
    }
}