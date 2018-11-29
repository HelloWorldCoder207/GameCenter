package fall2018.csc2017.game_centre.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * View class, excluded from unit test.
 * The logic of change background feature.
 */
class SlidingTilesImageProcessor {

    /**
     * the length of this game
     */
    private int boardLength;

    /**
     * the column width of board
     */
    private int columnWidth;

    /**
     * the column height of board
     */
    private int columnHeight;

    /**
     * Image parts of the user selected picture.
     */
    private List<BitmapDrawable> customImageTiles = new ArrayList<>();

    /**
     * Constructor for this image processor
     * @param boardLength  the length of this game
     * @param columnWidth  the column width of board
     * @param columnHeight the column height of board
     */
    SlidingTilesImageProcessor(int boardLength, int columnWidth, int columnHeight){
        this.boardLength= boardLength;
        this.columnWidth = columnWidth;
        this.columnHeight = columnHeight;
    }

    /**
     * Trim image into pieces according to the difficulty.
     * @param image image to trim
     */
    void trimImage(Bitmap image) {
        for (int col = 0; col < boardLength; col++) {
            for (int row = 0; row < boardLength; row++) {
                Bitmap tmp = Bitmap.createBitmap(image,
                        row * columnWidth, col * columnHeight, columnWidth, columnHeight);
                BitmapDrawable tile = new BitmapDrawable(tmp);
                customImageTiles.add(tile);
            }
        }
        customImageTiles.remove(customImageTiles.size() - 1);
    }

    /**
     * the getter for custom image tiles
     * @return a list of sliced image tiles
     */
    List<BitmapDrawable> getCustomImageTiles(){return customImageTiles;}
}
