package com.example.qr;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ProfileListFragment extends Fragment {

    public ProfileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        ListView listView = view.findViewById(R.id.listview_profiles);
        Button btnClose = view.findViewById(R.id.btn_close_profile_list);

        // TODO: Set up the ListView adapter here with profile data

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            // TODO: Handle profile item click, possibly show profile details
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    // TODO: Implement the adapter for the ListView to display the profiles
}
