package fall2018.csc2017.game_centre.sudoku;

import fall2018.csc2017.game_centre.R;

/**
 * Cell in sudoku board.
 */
class Cell {
    /**
     * the uncolored blank background id of the cell.
     */
    private int blankBackground = R.drawable.sudoku_blank;

    /**
     * the colored blank background id of the cell.
     */
    private int blankColoredBackground = R.drawable.sudoku_blank_coloured;

    /**
     * the uncolored numbered background id of the cell.
     */
    private int numberBackground;

    /**
     * the colored numbered background id of the cell.
     */
    private int coloredBackground;

    /**
     * determine if the cell is visible, i.e. if the cell is displaying the number or a blank.
     */
    boolean isVisible;

    /**
     * The value of the cell, from 1-9
     */
    private final int value;

    /**
     * Constructor of the cell.
     * @param value     the preset value of the cell.
     * @param isVisible the visibility of the cell.
     */
    Cell(int value, boolean isVisible){
        this.value = value;
        this.isVisible = isVisible;
    }

    /**
     * Setter for the number background, should be called from an Activity class.
     * @param numberBackground the id of the number background.
     */
    void setNumberBackground(int numberBackground) {
        this.numberBackground = numberBackground;
    }

    /**
     * Setter for the colored background, should be called from an Activity class.
     * @param coloredBackground the id of the number background.
     */
    void setColoredBackground(int coloredBackground) {
        this.coloredBackground = coloredBackground;
    }

    /**
     * Getter for the actual background.
     */
    int getBackground() {
        if (isVisible){
            return this.numberBackground;
        }
        return this.blankBackground;
    }

    /**
     * Getter for value.
     * @return the value of the cell.
     */
    int getValue() {
        return value;
    }

    /**
     * Change the invisible cell into visible.
     */
    void changeToVisible(){
        this.isVisible = true;
    }

    /**
     * String representation of the cell.
     */
    public String toString(){
        if (isVisible){
            return String.valueOf(value);
        }
        else {
            return String.valueOf(0);
        }
    }
}
