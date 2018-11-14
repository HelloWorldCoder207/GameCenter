package fall2018.csc2017.game_centre.slidingtiles;

import fall2018.csc2017.game_centre.R;

/**
 * Tiles for easy difficulty.
 */
class EasyTile extends Tile {

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the Id of the image
     */
    EasyTile(int backgroundId) {
        super(backgroundId);
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.tile_1;
                break;
            case 2:
                background = R.drawable.tile_2;
                break;
            case 3:
                background = R.drawable.tile_3;
                break;
            case 4:
                background = R.drawable.tile_4;
                break;
            case 5:
                background = R.drawable.tile_5;
                break;
            case 6:
                background = R.drawable.tile_6;
                break;
            case 7:
                background = R.drawable.tile_7;
                break;
            case 8:
                background = R.drawable.tile_8;
                break;
            case 9:
                background = R.drawable.tile_blank;
                break;
        }
    }
}
