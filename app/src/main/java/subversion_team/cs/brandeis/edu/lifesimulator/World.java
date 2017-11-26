package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class World extends AppCompatActivity {

    private RelativeLayout world;
    private FrameLayout frame;
    private static StaticView staticView;
    private int timeRate, size, boardLength, sideLength;
    private Button button_start, button_stop;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        Intent it = getIntent();

        this.timeRate = it.getIntExtra("timeRate", 250);
        //Log.d("xiiiiiiiiiiiiiiiiii", "timerate: "+this.timeRate);
        this.size = it.getIntExtra("size", 10);

        adjustWindow();
        setView();
        setButton();
        setTraceTouch();

    }

    private void adjustWindow(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.boardLength = displayMetrics.widthPixels;
        this.sideLength = boardLength/size;
    }

    private void setView(){
        this.world = (RelativeLayout) findViewById(R.id.world);
        this.frame = (FrameLayout) findViewById(R.id.frameLayout);
        this.button_start = (Button) findViewById(R.id.button_start);
        this.button_stop = (Button) findViewById(R.id.button_stop);
        this.staticView = new StaticView(this, size, sideLength);
        ViewGroup.LayoutParams lp = frame.getLayoutParams();
        lp.width = boardLength;
        lp.height = boardLength;
        frame.setLayoutParams(lp);
        frame.addView(staticView);
    }


    public void setButton(){
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("xiiiiiiiiiiiiiiiiii", "timerate2: "+timeRate);

                if(timeRate == 0) {
                    staticView.grid.next();
                    staticView.postInvalidate();
                } else {
                    new Thread(new SignalLightThread()).start();
                }
            }
        });

    }
    public void stop(View view) {
        Log.d("xiiiiiiiiiiiiiiiiii", "stop: ");
        Log.d("what the heck", Thread.currentThread().getName());
        //Thread.currentThread().interrupt();
        flag = true;

    }

    private void setTraceTouch(){
        world.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d("xiiiiiiiiiiiiiiiiiii", "touching: ");
                int x, y;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = (int)event.getX()/sideLength;
                        y = (int)event.getY()/sideLength;
                        staticView.grid.setBorn(x,y);
                        //Log.w("x: "+x," y: " + y );
                        break;
                }
                staticView.invalidate();
                return true;
            }
        });
    }



    class SignalLightThread implements Runnable{

        @Override
        public void run() {
            Log.d("Shiiiiiiiit", Thread.currentThread().getName());
            while(!flag){
                Log.d("xiiiiiiiiiiiiiiiiii", "timerate3: "+timeRate);
                try {
                    Thread.sleep(timeRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                staticView.grid.next();
                staticView.postInvalidate();
            }
        }
    }
}
