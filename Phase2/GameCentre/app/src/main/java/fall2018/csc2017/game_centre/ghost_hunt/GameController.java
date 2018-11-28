package fall2018.csc2017.game_centre.ghost_hunt;

import android.content.Context;

import java.util.Observable;
import java.util.Stack;

import fall2018.csc2017.game_centre.Undoable;

/**
 * Controller
 *
 * Handle game events.
 */
class GameController extends Observable implements Undoable {

    /**
     * Argument indicating change of board.
     */
    static final Integer BOARD_CHANGE = 0;

    /**
     * Argument indicating end of game.
     */
    static final Integer GAME_OVER = 1;

    /**
     * Argument indicating level is over.
     */
    static final Integer LEVEL_OVER = 2;

    /**
     * Argument indicating finish of game.
     */
    static final Integer GAME_FINISH = 3;

    /**
     * How many moves a ghost can make in a round.
     */
    private static final int GHOST_MOVE_PER_ROUND = 2;

    /**
     * Context of the activity.
     */
    private Context context;

    /**
     * File handler for the controller.
     */
    private FileHandler fileHandler;

    /**
     * Board manager.
     */
    private GameState state;

    /**
     * Maximum of undo time.
     */
    private static final int MAX_UNDO = 5;

    /**
     * Player move undo stack.
     */
    private Stack<Direction> playerUndoStack = new Stack<>();

    /**
     * Ghost move undo stack.
     */
    private Stack<Direction> ghostUndoStack = new Stack<>();

    /**
     * Constructor for controller.
     * @param context context of activity
     */
    GameController(Context context, GameState state) {
        this.context = context;
        this.state = state;
        this.fileHandler = FileHandler.getInstance();
        this.fileHandler.setState(this.state);
    }

    /**
     * Setter for context
     * @param context context
     */
    void setContext(Context context) {
        this.context = context;
    }

    /**
     * Getter for board manager.
     * @return board manager
     */
    GameState getState() {
        return this.state;
    }

    /**
     * Setter for state.
     * @param state new state
     */
    void setState(GameState state) {
        this.state = state;
    }

    /**
     * Start new game.
     */
    void startGame() {
        this.fileHandler.loadMap(context, 1);
        this.state = new GameState(fileHandler.getBoard());
        this.fileHandler.setState(state);
    }

    /**
     * Load existing game.
     * @return if there is saved game
     */
    boolean loadGame() {
        fileHandler.loadGame(context);
        this.state = fileHandler.getState();
        return state != null;
    }

    /**
     * Save the game.
     */
    void saveGame() {
        fileHandler.setState(this.state);
        fileHandler.saveGame(context);
    }

    /**
     * Performs a move undo on the game.
     */
    @Override
    public void undo() {

    }

    /**
     * Process direction change.
     * @param direction direction of going
     */
    void processEvent(Direction direction) {
        Board board = state.getBoard();
        Player player = board.getPlayer();
        Ghost ghost = board.getGhost();
        processEntityMove(player, direction);
        state.incrementMoveCount();
        for (int i = 0; i < GHOST_MOVE_PER_ROUND; i++) {
            Direction nextDir = ghost.getNextDirection(player.getRow(), player.getCol());
            processEntityMove(ghost, nextDir);
        }
        autoSave();
    }

    /**
     * Auto saving logic.
     */
    private void autoSave() {
        if (state.getMoveCount() % 5 == 0) {
            saveGame();
        }
    }

    /**
     * Process an entity's move on certain direction.
     * @param entity entity to make move
     * @param direction direction of move
     */
    private void processEntityMove(Entity entity, Direction direction) {
        if (isValidMove(entity.getRow(), entity.getCol(), direction)) {
            entity.move(direction);
            appendMove();
        } else {
            entity.setDirection(direction);
        }
        notifyChange();
    }

    private void appendMove() {
        
    }

    /**
     * Determine if the move is a valid one.
     * @param direction direction to move
     * @return if the move is valid or not
     */
    private boolean isValidMove(int row, int col, Direction direction) {
        Board board = state.getBoard();
        return board.getTile(row, col).getAvailableMoves().contains(direction);
    }

    /**
     * Notify change of the board accordingly.
     */
    private void notifyChange() {
        setChanged();
        if (gameOver()) {
            notifyObservers(GAME_OVER);
        } else {
            if (levelOver()) {
                if (state.getBoard().getLevel() < GameState.MAX_LEVEL) {
                    setNextLevel();
                    notifyObservers(LEVEL_OVER);
                } else {
                    notifyObservers(GAME_FINISH);
                }
            } else {
                notifyObservers(BOARD_CHANGE);
            }
        }
    }

    /**
     * Set the board to next level.
     */
    private void setNextLevel() {
        int current_level = this.state.getBoard().getLevel();
        fileHandler.loadMap(context, current_level + 1);
        this.state.setBoard(fileHandler.getBoard());
    }

    /**
     * Determine if the current level is finished.
     * @return if level completed
     */
    private boolean levelOver() {
        Board board = state.getBoard();
        int row = board.getPlayer().getRow();
        int col = board.getPlayer().getCol();
        return board.getTile(row, col).isExit();
    }

    /**
     * Determine if the game is over.
     * @return if game is over
     */
    private boolean gameOver() {
        Player p = state.getBoard().getPlayer();
        Ghost g = state.getBoard().getGhost();
        return p.getRow() == g.getRow() && p.getCol() == g.getCol();
    }
}
