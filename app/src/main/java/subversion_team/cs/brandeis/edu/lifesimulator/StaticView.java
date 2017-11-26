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

    public StaticView (Context context, int size, int sideLength) {
        super(context);
        this.painter = new Paint();
        this.size = size;
        this.sideLength = sideLength;
        this.grid = new Grid(size);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public Grid getGrid(){ return grid; }

    @Override
    protected void onDraw(Canvas canvas) {

        painter.setStrokeWidth(3.0f);
        //grid.next();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (grid.grid[i][j].getState()){
                    case BORN:
                        painter.setColor(Color.argb(100 ,128, 255, 128)); break;
                    case ALIVE:
                        painter.setColor(Color.argb(100, 255, 51, 0)); break;
                    case DIED:
                        //painter.setColor(Color.argb(100, 255, 166, 77)); break;
                        painter.setColor(Color.argb(100, 80, 152, 193)); break;
                    case EMPTY:
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
