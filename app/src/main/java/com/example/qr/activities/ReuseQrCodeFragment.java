package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReuseQrCodeFragment extends Fragment {

    private Button btnCancel;

    public ReuseQrCodeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reuse_qr, container, false);
        super.onCreate(savedInstanceState);

        btnCancel = view.findViewById(R.id.btnCancel);

        // citation: OpenAI, ChatGPT 4, 2024: How do I click a button to change fragments
        // in android studio
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of OrganizerFragment
                CreateEventFragment createEventFragment = new CreateEventFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Use getFragmentManager() in a Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with OrganizerFragment. Assume R.id.fragment_container is the ID of your FrameLayout
                fragmentTransaction.replace(R.id.fragment_container, createEventFragment);
                // fragmentTransaction.addToBackStack(null); // Optional: Add this transaction to the back stack
                fragmentTransaction.commit(); // Commit the transaction
            }
        });
        // end citation

        return view;
    }
}
