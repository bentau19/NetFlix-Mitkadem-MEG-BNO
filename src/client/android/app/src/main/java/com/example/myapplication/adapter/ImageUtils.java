package com.example.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageUtils {
    public static Bitmap hexToImage(String hexString) {
        try {
            // Remove prefix if exists
            if (hexString.startsWith("0x")) {
                hexString = hexString.substring(2);
            }

            // Convert hex to byte array
            byte[] imageBytes = hexStringToByteArray(hexString);

            // Convert byte array to bitmap
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}
