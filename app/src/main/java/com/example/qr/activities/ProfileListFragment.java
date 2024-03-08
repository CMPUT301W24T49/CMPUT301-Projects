package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.User;
import com.example.qr.models.UserArrayAdapter;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class ProfileListFragment extends Fragment {
    ListView userList;
    ArrayList<User> userDataList;
    UserArrayAdapter userArrayAdapter;
    private int positionToEdit;
    private FirebaseFirestore db;
    public ProfileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        ListView listView = view.findViewById(R.id.listview_profiles);
        Button btnClose = view.findViewById(R.id.btn_close_profile_list);

        // TODO: Replace with actual image data
        db = FirebaseFirestore.getInstance();
        userDataList = new ArrayList<>();
        userArrayAdapter = new UserArrayAdapter(getContext(), userDataList);
        listView.setAdapter(userArrayAdapter);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;
                User clickedProfile = (User) adapterView.getAdapter().getItem(position);
                AdminUserProfileDetailFragment addCityFragment = AdminUserProfileDetailFragment.newInstance(clickedProfile);
                addCityFragment.show(getParentFragmentManager(), "Profile Detail");
            }
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void fetchData() {

        FirebaseUtil.fetchCollection("Users", User.class, new FirebaseUtil.OnCollectionFetchedListener<User>() {
            @Override
            public void onCollectionFetched(List<User> userList) {
                // Handle the fetched user here
                userDataList.addAll(userList);
                userArrayAdapter.notifyDataSetChanged();
                Log.d("UserListFragment", "Fetched " + userList.size() + "User");
            }

            @Override
            public void onError(Exception e) {
                // Handle any errors here
            }


        });
    }
}
