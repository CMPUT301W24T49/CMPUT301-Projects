package com.example.qr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.EventArrayAdapter;
import com.example.qr.models.Notification;
import com.example.qr.models.NotificationArrayAdapter;
import com.example.qr.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificationListFragment extends Fragment {
    private String eventID;
    private Event event;
    ArrayList<Notification> notificationsDataList;
   NotificationArrayAdapter notificationArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventID = (String) getArguments().getSerializable("event_key");
            event = (Event) getArguments().getSerializable("event");

        }
        View view = inflater.inflate(R.layout.notification_list_view, container, false);

        ListView listView = view.findViewById(R.id.listview_notification);
//        Button btnClose = view.findViewById(R.id.btn_close_event_list);

        notificationsDataList = new ArrayList<>();
        notificationArrayAdapter = new NotificationArrayAdapter(getContext(), notificationsDataList);
        listView.setAdapter(notificationArrayAdapter);
        fetchData();
        TextView eventTitle = view.findViewById(R.id.event_title_notification);
        eventTitle.setText(event.getTitle());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                Notification clickedNotification = (Notification) adapterView.getAdapter().getItem(position);
                Boolean readStatus = clickedNotification.getReadStatus();
                if (readStatus == null) {
                    readStatus = Boolean.FALSE; // Set a default value if null
                }
                clickedNotification.setReadStatus(!readStatus);
                notificationArrayAdapter.notifyDataSetChanged();
            }
            });
        return view;
    }

    private void fetchData() {
        String pathToNotifications = "Events/" + eventID + "/Notifications";
        FirebaseUtil.fetchCollection(pathToNotifications, Notification.class, new FirebaseUtil.OnCollectionFetchedListener<Notification>() {
            @Override
            public void onCollectionFetched(List<Notification> notificationList) {
                notificationsDataList.addAll(notificationList);
                notificationArrayAdapter.notifyDataSetChanged();   // Update notification array adapter
                Log.d("NotificationFragment", "Fetched " + notificationList.size() + " notifications for eventID: " + eventID);
            }
            @Override
            public void onError(Exception e) {
                Log.e("NotificationFragment", "Error fetching notifications for eventID: " + eventID, e);
            }
        });
    }



}
