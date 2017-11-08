package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by brucewilliam on 11/1/17.
 */

import java.util.*;

public class Grid {
    int size;
    Cell[][] grid;

    public Grid(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        populateGrid();
    }

    public void populateGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = new Cell();
            }
        }
    }

    public void next() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j].setState(this.nextState(this.grid[i][j], i, j));
            }
        }
    }

    public CellState nextState(Cell currCell, int row, int col) {
                //then get its state
                int livingNeighbours = numOfLivingCellsNeighbours(row, col);
                if (currCell.ifAlive()) {
                    if (livingNeighbours == 2 || livingNeighbours == 3) return CellState.ALIVE;
                    else return CellState.DIED;
                } else {
                    if (livingNeighbours == 3) return CellState.BORN;
                }
                return CellState.EMPTY;
        }

    public int numOfLivingCellsNeighbours(int row, int col) {
        //need to check all its neighbours
        int counter = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                //now use wrapping mechanism to detect other neighbour cells
                int rowIndex = floorMod(row + i, size);
                int colIndex = floorMod(col + j, size);
                //then check the cell state for this position
                if (this.grid[rowIndex][colIndex].ifAlive()) counter++;
            }
        }
        return counter;
    }

    public int floorMod(int divident, int divisor) {
        if (divident < 0) {
            return divisor - ((-divident)%divisor);
        }
        return divident%divisor;
    }
}
