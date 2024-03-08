package com.example.qr.utils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class to handle image picking and capturing operations.
 * TODO: FIX!!!!
 */
public class ImagePickerUtil {

    private final AppCompatActivity activity;
    private final ActivityResultLauncher<Intent> imagePickerLauncher;
    private final ActivityResultLauncher<Uri> imageCaptureLauncher;

    private static final int COMPRESSION_QUALITY = 50;

    /**
     * Constructor for ImagePickerUtil.
     *
     * @param activity The activity that will use the image picker.
     * @param listener The listener for image picked event.
     */
    public ImagePickerUtil(AppCompatActivity activity, OnImagePickedListener listener) {
        this.activity = activity;

        // Launcher for picking image from gallery
        imagePickerLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            listener.onImagePicked(compressImageUri(imageUri));
                        }
                    }
                });

        // Launcher for capturing image from camera
        imageCaptureLauncher = activity.registerForActivityResult(new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess) {
                        listener.onImagePicked(compressImageUri(currentPhotoUri));
                    }
                });
    }

    private Uri currentPhotoUri;

    public interface OnImagePickedListener {
        void onImagePicked(Uri imageUri);
    }

    /**
     * Launches the image picker to select an image from the gallery.
     */
    public void pickImageFromGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(pickPhotoIntent);
    }

    /**
     * Launches the camera to capture an image.
     */
    public void captureImageFromCamera() {
        try {
            currentPhotoUri = createImageFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageCaptureLauncher.launch(currentPhotoUri);
    }

    /**
     * Compresses the image to reduce its size. Source: Chat GPT 4, 2024
     *
     * @param imageUri The Uri of the image to compress.
     * @return The Uri of the compressed image.
     */
    private Uri compressImageUri(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, out);
            String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray())), "Title", null);
            return Uri.parse(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads an image from a URL and returns it as a Drawable.
     *
     * @param url The URL of the image to load.
     * @return The Drawable of the image.
     */
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a temporary image file to store the captured image.
     *
     * @return The Uri of the created image file.
     * @throws IOException If an I/O error occurs.
     */
    private Uri createImageFile() throws IOException {
        String fileName = "JPEG_" + System.currentTimeMillis();
        File storageDir = activity.getExternalFilesDir(null);
        File image = File.createTempFile(fileName, ".jpg", storageDir);
        return Uri.fromFile(image);
    }
}