package fall2018.csc2017.game_centre.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import fall2018.csc2017.game_centre.slidingtiles.SlidingTilesImageProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static org.junit.Assert.*;

public class SlidingTilesImageProcessorTest {

    private Bitmap bitmap;
    SlidingTilesImageProcessor testProcessor;
    Tile tile;
    @Before
    public void setUp() throws Exception {
        bitmap=createBitmap((DisplayMetrics) null, 90, 90, Bitmap.Config.ARGB_8888);
        testProcessor=new SlidingTilesImageProcessor(90,30,30);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void trimImage() {
        testProcessor.trimImage(bitmap);
        List<BitmapDrawable> list=testProcessor.getCustomImageTiles();
        for(BitmapDrawable tile:list)    {
            assertEquals(tile.getIntrinsicHeight(), 30);
        }


    }

    @Test
    public void getCustomImageTiles() {
    }
}