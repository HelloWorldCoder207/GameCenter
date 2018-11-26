package fall2018.csc2017.game_centre.sudoku;

/**
 * Cell in sudoku board.
 */
class Cell {
    /**
     * the background id of the cell.
     */
    int background;

    /**
     * determine if the cell is visible, i.e. if the cell is displaying the number or a blank.
     */
    private boolean isVisible;

    /**
     * The value of the cell, from 1-9
     */
    private int value;

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
     * Setter for the background, should be called from an Activity class.
     * @param background the id of the background.
     */
    void setBackground(int background) {
        this.background = background;
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
