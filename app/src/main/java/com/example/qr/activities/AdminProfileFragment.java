package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
/**
 * AdminProfileFragment provides an interface for viewing and editing administrative user profile details.
 */

public class AdminProfileFragment extends Fragment {

    public AdminProfileFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    }
}
