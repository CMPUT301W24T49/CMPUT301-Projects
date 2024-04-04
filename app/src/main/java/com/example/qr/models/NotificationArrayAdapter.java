package com.example.qr.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/*
Purpose - BookArrayAdapter defines how a list of Book is displayed within ListView.
Design Rationale - BookArrayAdapter displays details of books for each book within ListView with a blue dot indicator for unread books.
*/
public class NotificationArrayAdapter extends ArrayAdapter<Notification> {
    private Spinner spinner;
    private TextView message, timeText;
    private ImageView readButton;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());


    public NotificationArrayAdapter(Context context, ArrayList<Notification> notification) {
        super(context, 0, notification);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.notification_list_view_detail, parent, false);
        }

        message = view.findViewById(R.id.message); // Assuming this is the TextView for the book's name
        timeText = view.findViewById(R.id.time_text); // TextView for the time
        spinner = view.findViewById(R.id.book_details_spinner);
        readButton = view.findViewById(R.id.button); // The blue dot indicator

        Notification notification = getItem(position);

        // Set the book name
        message.setText(notification.getMessage());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean currentStatus = notification.getReadStatus();
                boolean newStatus;
                if (currentStatus == null) {
                    newStatus = true;
                } else {
                    newStatus = !currentStatus;
                }
                notification.setReadStatus(newStatus);
                notifyDataSetChanged();
            }
        });


        Boolean readStatus = notification.getReadStatus();
        if (readStatus != null) {
            readButton.setVisibility(readStatus ? View.GONE : View.VISIBLE);
        } else {
            // Decide on a default behavior if readStatus is null. For example, hiding the indicator:
            readButton.setVisibility(View.GONE);
        }

        timeText.setText(dateFormat.format(notification.getSentTime()));


        ArrayList<String> bookDetails = new ArrayList<>();
        bookDetails.add("Message");
        bookDetails.add(notification.getMessage());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bookDetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }
}
