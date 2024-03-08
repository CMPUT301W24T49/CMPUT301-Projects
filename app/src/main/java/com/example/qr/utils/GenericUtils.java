package com.example.qr.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.type.LatLng;

import java.io.IOException;
import java.util.List;

public class GenericUtils {

    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng LatLan= null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);

            LatLan = LatLng.newBuilder()
                    .setLatitude(location.getLatitude())
                    .setLongitude(location.getLongitude())
                    .build();

        } catch (IOException ex) {

            throw new RuntimeException(ex);
        }
        return LatLan;
    }
}
