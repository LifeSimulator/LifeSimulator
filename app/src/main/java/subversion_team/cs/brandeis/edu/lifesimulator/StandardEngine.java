package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by brucewilliam on 11/6/17.
 */

import java.util.*;

public class StandardEngine {
    //it talks to the simulation activitiy through engine delegate
    EngineDelegate delegate;
    Grid grid;
    Timer timer;
    double refreshRate = 0.0;
    int size;
    Map<String , Integer> stats;

    static StandardEngine SHAREDINSTANCE = new StandardEngine(10);

    public StandardEngine(int size) {
        this.grid = new Grid(size);
        this.timer = new Timer();
        this.size = size;
        this.stats = new HashMap<String, Integer>();
        stats.put("empty", size*size);
        stats.put("born", 0);
        stats.put("living", 0);
        stats.put("dead", 0);
    }

    public void step() {
        this.grid.next();
    }
}
