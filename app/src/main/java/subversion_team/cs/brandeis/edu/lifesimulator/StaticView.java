package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by xichen on 11/21/17.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


public class StaticView extends View {

    private int size, sideLength;
    public Grid grid;
    private Paint painter;

    public static int born, alive, empty, dead, iter;

    public StaticView (Context context, int size, int sideLength) {
        super(context);
        this.painter = new Paint();
        this.size = size;
        this.sideLength = sideLength;
        this.grid = new Grid(size);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }
    
    public void resetGrid() {
        Log.d("xiiiiiiiiiiiiiiiiii", "reset grid: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.grid[i][j].setState(CellState.EMPTY);
            }

        }
    }

    public void resetData(){
        born = 0;
        alive = 0;
        empty = 0;
        dead = 0;
        iter = 0;
    }

    public Grid getGrid(){ return grid; }

    @Override
    protected void onDraw(Canvas canvas) {

        painter.setStrokeWidth(3.0f);
        //grid.next();
        iter++;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (grid.grid[i][j].getState()){
                    case BORN:
                        born++;
                        painter.setColor(Color.argb(100 ,128, 255, 128)); break;
                    case ALIVE:
                        alive++;
                        painter.setColor(Color.argb(100, 255, 51, 0)); break;
                    case DIED:
                        dead++;
                        //painter.setColor(Color.argb(100, 255, 166, 77)); break;
                        painter.setColor(Color.argb(100, 80, 152, 193)); break;
                    case EMPTY:
                        empty++;
                        painter.setColor(Color.argb(100, 80, 152, 193)); break;
                    default:
                        break;
                }
                canvas.drawRect(sideLength * i + 5, sideLength * j+5, sideLength *(i+1) - 5, sideLength*(j+1)-5, painter);
                Log.w("draw","");
            }

        }
    }


}
