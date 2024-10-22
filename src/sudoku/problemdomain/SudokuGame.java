package sudoku.problemdomain;

import sudoku.computationlogic.SudokuUtilities;
import sudoku.constants.GameState;

import java.io.Serializable;

public class SudokuGame implements Serializable {
    private final GameState gamestate;
    private final int [][] gridState;

    public static final int GRID_BOUNDARY =9;

    public SudokuGame(GameState gamestate, int [][] gridState) {
        this.gamestate = gamestate;
        this.gridState = gridState;
    }
    public GameState getGameState() {
        return gamestate;
    }

    public int [][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
