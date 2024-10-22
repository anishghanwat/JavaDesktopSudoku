package sudoku.buildlogic;

import sudoku.constants.GameState;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();
        try {
            // Try to retrieve existing game data
            initialState = storage.getGameData();
        } catch (IOException e) {
            // If there is an exception, create a new game state with GameState.NEW and an empty board
            initialState = new SudokuGame(GameState.NEW, getEmptyBoard());
            try {
                // Attempt to update the game data storage with the new game state
                storage.updateGameData(initialState);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        // Initialize the Control Logic and set the listener
        IUserInterfaceContract.EventListner uiLogic = new ControlLogic(storage, userInterface);

        // Set the listener and update the UI with the initial game state
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }

    // Method to create an empty 9x9 Sudoku board (all values set to 0)
    private static int[][] getEmptyBoard() {
        return new int[9][9];  // Returns a new 9x9 int array with all values initialized to 0
    }
}
