package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qr.R;

import java.util.HashMap;
import java.util.Map;

/**
 * AdminProfileFragment provides an interface for viewing and editing administrative user profile details.
 */
public class AdminProfileFragment extends Fragment {

    public AdminProfileFragment() {
        // Required empty public constructor
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

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("firstName")) {
                editTextFirstName.setText(args.getString("firstName"));
            }
            if (args.containsKey("lastName")) {
                editTextLastName.setText(args.getString("lastName"));
            }
            if (args.containsKey("email")) {
                editTextEmail.setText(args.getString("email"));
            }
            if (args.containsKey("phone")) {
                editTextPhone.setText(args.getString("phone"));
            }
        }

        saveButton.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String email = editTextEmail.getText().toString();
            String phone = editTextPhone.getText().toString();

            //need to implement saving these details
        });

    }
}
