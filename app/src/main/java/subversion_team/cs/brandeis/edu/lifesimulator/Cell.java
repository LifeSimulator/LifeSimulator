package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by brucewilliam on 11/1/17.
 */

public class Cell {
    private CellState state;
    private int[][] neighbours;

    Cell() {
        this.state = CellState.EMPTY;
    }

    boolean ifAlive() {
        switch (this.state) {
            case ALIVE: case BORN:
                return true;
            case EMPTY: case DIED:
                return false;
            default:
                return false;
        }
    }

    /*
     * @param, currState
     * @return a new cellstate
     * @TO-DO, change the cell state based on its previous state
     */
    CellState toggle(CellState currState) {
        switch (currState) {
            case EMPTY: case DIED:
                return CellState.ALIVE;
            case ALIVE: case BORN:
                return CellState.EMPTY;
            default:
                return null;
        }
    }

    CellState getState() { return this.state; }

    void setState(CellState state) { this.state = state; }

}
