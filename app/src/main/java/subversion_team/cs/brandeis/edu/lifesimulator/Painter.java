package subversion_team.cs.brandeis.edu.lifesimulator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;


public class Painter extends View {

    private static final String LIVE_CELL_HEX = "#CC99FF";
    private static final String DEAD_CELL_HEX = "#FF66CC";

    private static Paint liveCellColor;
    private static Paint deadCellColor;

    private int boardWidth;
    private int boardHeight;

    public Painter(Context context) {
        super(context);

        // Init Paint colors
        liveCellColor = new Paint();
        deadCellColor = new Paint();
        liveCellColor.setColor(Color.parseColor(LIVE_CELL_HEX));
        deadCellColor.setColor(Color.parseColor(DEAD_CELL_HEX));

        // Init Canvas dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        this.boardHeight = displayMetrics.heightPixels;
        this.boardWidth = displayMetrics.widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCheckerboard(canvas);
    }

    private void drawCheckerboard(Canvas canvas) {
        int numX = 10;
        int cellWidth = this.boardWidth / numX;
        int cellHeight = cellWidth;

        int numY = this.boardHeight / cellWidth;

        int topLeftX = 0;
        int topLeftY = 0;
        int bottomRightX = cellWidth;
        int bottomRightY = cellHeight;

        int picker = 0;

        for (int i = 0; i < numY; i++) {
            for (int k = 0; k < numX; k++) {
                Paint currentColor = null;
                if (k % 2 == picker)
                    currentColor = liveCellColor;
                else
                    currentColor = deadCellColor;
                canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, currentColor);
                topLeftX += cellWidth;
                bottomRightX += cellWidth;
            }
            // Increment Y values for next row
            topLeftY += cellHeight;
            bottomRightY += cellHeight;
            // Reset X values back to beginning
            topLeftX = 0;
            bottomRightX = cellWidth;
            // Alternate colors for next row
            picker = (picker == 0) ? 1 : 0;
        }
    }
}