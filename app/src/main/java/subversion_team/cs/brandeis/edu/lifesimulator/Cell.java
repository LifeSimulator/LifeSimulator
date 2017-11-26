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


    CellState getState() { return this.state; }

    void setState(CellState state) { this.state = state; }

}
