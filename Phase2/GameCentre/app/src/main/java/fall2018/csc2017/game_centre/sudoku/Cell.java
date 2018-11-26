package fall2018.csc2017.game_centre.sudoku;

class Cell {
    int background;
    private boolean isVisible;
    private int value;

    Cell(int value, boolean isVisible){
        this.value = value;
        this.isVisible = isVisible;
    }

    void setBackground(int background) {
        this.background = background;
    }

    int getValue() {
        return value;
    }

    void changeToVisible(){
        this.isVisible = true;
    }

    public String toString(){
        if (isVisible){
            return String.valueOf(value);
        }
        else {
            return String.valueOf(0);
        }
    }
}
