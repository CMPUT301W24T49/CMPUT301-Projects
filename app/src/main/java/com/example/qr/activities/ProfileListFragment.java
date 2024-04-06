package com.example.qr.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.User;
import com.example.qr.models.UserArrayAdapter;
import com.example.qr.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
/**
 * ProfileListFragment displays a list of user profiles, offering options to view or edit detailed profile information.
 */
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

        userDataList = new ArrayList<>();
        userArrayAdapter = new UserArrayAdapter(getContext(), userDataList);
        listView.setAdapter(userArrayAdapter);

        fetchData();
//        Toast.makeText(getActivity(), userDataList.get(0).getName(), Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                positionToEdit = position;
                User clickedProfile = (User) adapterView.getAdapter().getItem(position);
                AdminUserProfileDetailFragment userFragment = AdminUserProfileDetailFragment.newInstance(clickedProfile);
                userFragment.setTargetFragment(ProfileListFragment.this, 0);
                userFragment.show(getParentFragmentManager(), "Profile Detail");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User userToBeDeleted = userDataList.get(position);

                // Construct an AlertDialog for confirmation
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete User") // Set the dialog title
                        .setMessage("Are you sure you want to delete " + userToBeDeleted.getName() + "?") // Set the dialog message
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Proceed with deletion if "Yes" is clicked
                            FirebaseUtil.deleteUser(userToBeDeleted.getId(),
                                    aVoid -> {
                                        userDataList.remove(position); // Remove the user from the list
                                        userArrayAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
                                        fetchData(); // Refresh data
                                        Toast.makeText(getActivity(), "User " + userToBeDeleted.getId() + " deleted successfully", Toast.LENGTH_SHORT).show();
                                    },
                                    e -> {
                                        Toast.makeText(getActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("No", null) // No action if "No" is clicked
                        .setIcon(android.R.drawable.ic_dialog_alert) // Set an icon for the dialog
                        .show(); // Display the dialog

                return true; // Indicate the click was handled
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
//                Toast.makeText(getActivity(), String.valueOf(userDataList.size()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), userDataList.get(0).getName(), Toast.LENGTH_SHORT).show();
                Log.d("UserListFragment", "Fetched " + userList.size() + "User");
            }

            @Override
            public void onError(Exception e) {
                // Handle any errors here
            }


        });
    }
}
