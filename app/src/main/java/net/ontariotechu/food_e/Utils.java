package net.ontariotechu.food_e;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {

    /**
     * Loads image at specified url into the provided Image View
     * @param url
     * @param imageView
     */
    public static void loadImage(String url, ImageView imageView) {
        Thread thread = new Thread(() -> {
            try {
                InputStream inputStream = new URL(url).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Update on ui thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    imageView.setImageBitmap(bitmap);
                });
            } catch (IOException ex) {

            }
        });
        thread.start();
    }
}
