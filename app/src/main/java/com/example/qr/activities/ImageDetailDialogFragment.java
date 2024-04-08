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
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import com.example.qr.models.Image;
import com.example.qr.models.User;

import java.text.SimpleDateFormat;

/**
 * ImageDetailDialogFragment provides a detailed view of an image, including options to delete or cancel the view.
 */
public class ImageDetailDialogFragment extends DialogFragment {
    interface ImageDetailDialogListener {

    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ImageDetailDialogListener listener;
    private Image image;
    // Assume you pass the image details as arguments

    /**
     *
     * New instance
     *
     * @param imageUrl  the image url.
     * @param title  the title.
     * @param imageId  the image identifier.
     * @return ImageDetailDialogFragment
     */
    public static ImageDetailDialogFragment newInstance(String imageUrl, String title, String imageId) {

        ImageDetailDialogFragment fragment = new ImageDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("image_url", imageUrl);
        args.putString("title_name", title);
        args.putString("image_id", imageId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override

/**
 *
 * On attach
 *
 * @param context  the context.  It is NonNull
 */
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        if (context instanceof AdminUserProfileDetailFragment.UserDetailDialogListener) {
            listener = (ImageDetailDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ImageDetailDialogListener");
        }
    }

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

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image_detail, null);

        String imageUrl = getArguments().getString("image_url");
        String name = getArguments().getString("title_name");
        String imageId = getArguments().getString("image_id");
        ImageView imageDetailView = view.findViewById(R.id.image_detail);

        Glide.with(this)
                .load(imageUrl)
                .into(imageDetailView);


        TextView textTitle = view.findViewById(R.id.image_id);
        TextView imageRelatedTo = view.findViewById(R.id.image_relatedTo);

        textTitle.setText(imageId);
//        // this needs to be checked
//        imageDetailView.setImageURI(Uri.parse(image.getUrl()));
//        imageUploadedBY.setText(image.getUploadedBy());
//        imageUploadTime.setText(dateFormat.format(image.getUploadTime()));
        imageRelatedTo.setText(name);



        // ... Initialize other TextViews for details as needed ...

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .create();
    }
}
