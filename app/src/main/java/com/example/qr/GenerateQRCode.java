package com.example.qr;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQRCode {
    // citation: OpenAI, ChatGPT4, 2024, I want a seperate class from main activity that
    // will create the QR code in android studio
    public static Bitmap generateQR(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        // in pixels set height and width of QR code
        int width = 500;
        int height = 500;
        try {
            // 2D matrix of bits that makes up the QR code. Essentially,
            // it's a grid where each cell (or bit) can be either on (1) or off (0)
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            // creates an empty bitmap with the specified dimensions and color configuration.
            // mutable bitmap with specified width and height, configuration for storing colors.
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // change pixel colours in a grid of x and y to black or white
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK :
                                    android.graphics.Color.WHITE);
                }
            }
            return bitmap;
            // if writer or createBitmap or setPixel have issues at any stage
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    // end of citation
}
