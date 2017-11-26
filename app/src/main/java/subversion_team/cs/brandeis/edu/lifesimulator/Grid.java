package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by brucewilliam on 11/1/17.
 */

import java.util.*;

public class Grid {
    int size;
    public Cell[][] grid;

    public Grid(int size) {
        //Log.d("xiiiiiiiiiiiiiiiiiii", "Gridnew: ");
        this.size = size;
        this.grid = new Cell[size][size];
        populateGrid(grid);
    }

    public void populateGrid(Cell[][] g) {
        //Log.d("xiiiiiiiiiiiiiiiiiii", "Gridpopulate: ");
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                g[i][j] = new Cell();
            }
        }

    }

    public void next() {
        Cell[][] tmpGrid = new Cell[size][size];
        populateGrid(tmpGrid);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Log.d("xiiiiiiiiiiiiiiiiiii", "x:"+i+" y:"+j);
                tmpGrid[i][j].setState(this.nextState(this.grid[i][j], i, j));
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j].setState(tmpGrid[i][j].getState());
            }
        }
    }

    public void setBorn(int i, int j){
        this.grid[i][j].setState(CellState.BORN);
    }

    public CellState nextState(Cell currCell, int row, int col) {
                //then get its state
                int livingNeighbours = numOfLivingCellsNeighbours(row, col);
                if (currCell.ifAlive()) {
                    if (livingNeighbours == 2 || livingNeighbours == 3) {
                        return CellState.ALIVE;
                    } else {
                        return CellState.DIED;
                    }
                } else {

                    if (livingNeighbours == 3) {
                        return CellState.BORN;
                    }
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
                if (this.grid[rowIndex][colIndex].ifAlive()) {
                    counter++;
                }
            }
        }
        //Log.d("xiiiiiiiiiiiiiiiiiii", "counter: "+counter);
        return counter;
    }

    public int floorMod(int divident, int divisor) {
        if (divident < 0) {
            return divisor - ((-divident)%divisor);
        }
        return divident%divisor;
    }
}
