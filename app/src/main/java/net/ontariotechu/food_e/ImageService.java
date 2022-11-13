package net.ontariotechu.food_e;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class ImageService {

    private static ImageService instance;

    // May need to use local storage for larger sets of images
    private ConcurrentHashMap<String, Bitmap> cached;

    private ImageService() {
        cached = new ConcurrentHashMap<>();
    }

    // Thread safe instantiation
    public static ImageService getInstance() {
        ImageService result = instance;
        if (result == null) {
            synchronized (ImageService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ImageService();
                }
            }
        }
        return result;
    }

    /**
     * Runs a background thread to get image by url, first checking if it is cached in dictionary
     * @param url The image URL
     * @param callback The method run, taking the image result as a parameter. If UI updates are
     *                 made then the callback must use a UiThread block
     */
    public void getImageBackground(String url, ImageResultCallback callback) {
        Thread thread = new Thread(() -> {
            try {
                Bitmap bitmap = getImage(url);
                callback.onComplete(bitmap);
            } catch (IOException ex) {
                callback.onComplete(null);
            }
        });
        thread.start();
    }

    /**
     * Get an image by url, first checking if it is cached in dictionary
     * @param url The image URL
     */
    public Bitmap getImage(String url) throws IOException {
        if (cached.get(url) == null) {
            InputStream inputStream = new URL(url).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            cached.put(url, bitmap);
            return bitmap;
        } else {
            return cached.get(url);
        }
    }

    public interface ImageResultCallback {
        void onComplete(Bitmap image);
    }

}
