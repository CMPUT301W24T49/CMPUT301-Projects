package com.example.qr.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.type.LatLng;

import java.io.IOException;
import java.util.List;

public class GenericUtils {

    /**
     * Converts an address to a latitude and longitude coordinate.
     *
     * @param context The context of the application.
     * @param strAddress The address to convert.
     * @return The latitude and longitude coordinate of the address.
     */
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
