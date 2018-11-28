package fall2018.csc2017.game_centre.sudoku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * View
 *
 * SudokuBoard grid view adapter.
 */
class SudokuGridViewAdapter extends BaseAdapter {

    /**
     * Cell buttons in SudokuBoard grid.
     */
    private ArrayList<Button> cellButtons;

    /**
     * Cell dimensions.
     */
    private int cellWidth;
    private int cellHeight;

    SudokuGridViewAdapter(ArrayList<Button> cellButtons, int cellWidth, int cellHeight) {
        this.cellButtons = cellButtons;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    /**
     * Size of cell buttons.
     *
     * @return count of buttons
     */
    @Override
    public int getCount() {
        return cellButtons.size();
    }

    /**
     * Get the button associated with the specified position.
     *
     * @param position position of the button
     * @return the button at the specified position
     */
    @Override
    public Object getItem(int position) {
        return cellButtons.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position the position of the item
     * @return the id of the button at the specified position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position.
     *
     * @param position    the position of the button
     * @param convertView the old view to reuse, if possible
     * @param parent      the parent that this view will eventually be attached to
     * @return a View corresponding to the button at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = cellButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(cellWidth, cellHeight);
        button.setLayoutParams(params);

        return button;
    }
}
