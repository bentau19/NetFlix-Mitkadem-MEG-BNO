package com.example.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils {

    public static Bitmap hexToImage(String hexString) {
        try {
            if (hexString == null || hexString.isEmpty()) {
                throw new IllegalArgumentException("Hex string cannot be null or empty.");
            }

            // Remove prefix if exists
            if (hexString.startsWith("0x")) {
                hexString = hexString.substring(2);
            }

            // Ensure the hex string length is even
            if (hexString.length() % 2 != 0) {
                throw new IllegalArgumentException("Hex string length must be even.");
            }

            // Convert hex to byte array
            byte[] imageBytes = hexStringToByteArray(hexString);

            // Convert byte array to bitmap
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging the error for better debugging
            return null;
        }
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
