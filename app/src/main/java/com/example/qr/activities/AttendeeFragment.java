package com.example.qr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.qr.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AttendeeFragment extends Fragment {

    private ActivityResultLauncher<Intent> qrScanLauncher;

    public AttendeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendee, container, false);

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnMyEvents = view.findViewById(R.id.btnMyEvents);
        Button btnBrowseEvents = view.findViewById(R.id.btnBrowseEvents);
        Button btnSettings = view.findViewById(R.id.btnSettings);
        Button btnCheckIn = view.findViewById(R.id.btnCheckIn);

        btnCheckIn.setOnClickListener(v -> {
            if (getActivity() != null) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
                integrator.setPrompt("");
                Intent intent = integrator.createScanIntent();
                qrScanLauncher.launch(intent);
            }
        });

        btnBrowseEvents.setOnClickListener(v -> {
            AttendeeBrowseEventListFragment eventListFragment = new AttendeeBrowseEventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnMyEvents.setOnClickListener(v -> {
            AttendeeMyEventListFragment myEventListFragment = new AttendeeMyEventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, myEventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnSettings.setOnClickListener(v -> {
            AttendeeSettingsFragment attendeeSettingsFragment = new AttendeeSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, attendeeSettingsFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        qrScanLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Handle the result using ZXing's IntentIntegrator
                        IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                        if (scanResult != null) {
                            if (scanResult.getContents() == null) {
                                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                                // Handle the scanned QR code content
                            }
                        }
                    }
                }
        );




        // Inflate the layout for this fragment
        return view;
    }
}
