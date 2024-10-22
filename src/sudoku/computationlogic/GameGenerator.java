package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    public static int[][] getNewGameGrid() {
        return unsolvedGame(getSolvedGame());
    }

    private static int[][] unsolvedGame(int[][] solvedGame) {

        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (!solvable) {
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            int index = 0;

            while (index < 40) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray;
    }

    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] grid = new int[GRID_BOUNDARY][GRID_BOUNDARY];  // Renamed from 'newGrid' to 'grid'

        for (int value = 1; value <= GRID_BOUNDARY; value++) {  // Starting value from 1 for Sudoku
            int allocations = 0;
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;

            while (allocations < GRID_BOUNDARY) {
                if (interrupt > 200) {
                    allocTracker.forEach(coords -> {
                        grid[coords.getX()][coords.getY()] = 0;  // Use 'grid' instead of 'newGrid'
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) {
                        clearArray(grid);  // Clear the grid if too many attempts are made
                        attempts = 0;
                        value = 1;  // Reset 'value' to restart the allocation process
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (grid[xCoordinate][yCoordinate] == 0) {
                    grid[xCoordinate][yCoordinate] = value;  // Use 'grid' instead of 'newGrid'

                    if (GameLogic.sudokuIsInvalid(grid)) {
                        grid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return grid;  // Return the correct 'grid'
    }

    private static void clearArray(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                grid[xIndex][yIndex] = 0;  // Clear the 'grid'
            }
        }
    }
}
