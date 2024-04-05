package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qr.R;
import com.example.qr.models.SharedViewModel;

/**
 * AdminProfileFragment provides an interface for viewing and editing administrative user profile details.
 */
public class AdminProfileFragment extends Fragment {
    private SharedViewModel viewModel;
    public AdminProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editTextFirstName = view.findViewById(R.id.editText_firstName);
        EditText editTextLastName = view.findViewById(R.id.editText_lastName);
        EditText editTextEmail = view.findViewById(R.id.editText_email);
        EditText editTextPhone = view.findViewById(R.id.editText_phone);
        Button saveButton = view.findViewById(R.id.button_save);

        viewModel.getFirstName().observe(getViewLifecycleOwner(), firstName -> {
            // This will be called every time the first name in the ViewModel changes
            if (firstName != null) {
                editTextFirstName.setText(firstName);
            }
        });

        // Repeat for last name, email, and phone
        viewModel.getLastName().observe(getViewLifecycleOwner(), lastName -> {
            if (lastName != null) {
                editTextLastName.setText(lastName);
            }
        });

        viewModel.getEmailAddress().observe(getViewLifecycleOwner(), email -> {
            if (email != null) {
                editTextEmail.setText(email);
            }
        });

        viewModel.getPhoneNumber().observe(getViewLifecycleOwner(), phone -> {
            if (phone != null) {
                editTextPhone.setText(phone);
            }
        });

        saveButton.setOnClickListener(v -> {
            viewModel.setFirstName(editTextFirstName.getText().toString());
            viewModel.setLastName(editTextLastName.getText().toString());
            viewModel.setEmailAddress(editTextEmail.getText().toString());
            viewModel.setPhoneNumber(editTextPhone.getText().toString());
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        // Retrieve the current text from each EditText
        View view = getView(); // Get the root view for the fragment
        if (view != null) {
            EditText editTextFirstName = view.findViewById(R.id.editText_firstName);
            EditText editTextLastName = view.findViewById(R.id.editText_lastName);
            EditText editTextEmail = view.findViewById(R.id.editText_email);
            EditText editTextPhone = view.findViewById(R.id.editText_phone);

            // Update the ViewModel with the current values
            viewModel.setFirstName(editTextFirstName.getText().toString());
            viewModel.setLastName(editTextLastName.getText().toString());
            viewModel.setEmailAddress(editTextEmail.getText().toString());
            viewModel.setPhoneNumber(editTextPhone.getText().toString());
        }
    }

}
