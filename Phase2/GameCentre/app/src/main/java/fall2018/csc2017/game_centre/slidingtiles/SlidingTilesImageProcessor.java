package fall2018.csc2017.game_centre.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * The logic of change background
 */
class SlidingTilesImageProcessor {

    /**
     * the row of this game
     */
    private int boardRow;

    /**
     * the col of this game
     */
    private int boardCol;

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
     * @param boardRow     the row of this game
     * @param boardCol     the col of this game
     * @param columnWidth  the column width of board
     * @param columnHeight the column height of board
     */
    SlidingTilesImageProcessor(int boardRow, int boardCol, int columnWidth, int columnHeight){
        this.boardRow = boardRow;
        this.boardCol = boardCol;
        this.columnWidth = columnWidth;
        this.columnHeight = columnHeight;
    }

    /**
     * Trim image into pieces according to the difficulty.
     * @param image image to trim
     */
    void trimImage(Bitmap image) {
        for (int col = 0; col < boardRow; col++) {
            for (int row = 0; row < boardCol; row++) {
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
