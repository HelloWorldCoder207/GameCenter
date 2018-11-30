package fall2018.csc2017.game_centre;

import android.graphics.Bitmap;

import java.io.Serializable;

class ProxyBitmap implements Serializable {
    private final int[] pixels;
    private final int width, height;

    ProxyBitmap(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
    }

    Bitmap getBitmap() {
        if (pixels != null) {
            return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
        }
        return null;
    }
}
