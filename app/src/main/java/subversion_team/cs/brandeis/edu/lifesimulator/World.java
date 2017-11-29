package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class World extends AppCompatActivity {

    private RelativeLayout world;
    private FrameLayout frame;
    private static StaticView staticView;
    private int timeRate, size, boardLength, sideLength;
    private Button button_start, button_stop;
    private boolean flag = false, restart = false;
    ImageButton toProfile;
    private int[][] seeds = null;

    public static int born, alive, empty, dead, iter;
    TextView borndata, alivedata,deaddata, emptydata, iterdata;// Added by Zhengayng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toProfile = (ImageButton) findViewById(R.id.user_profile) ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        Intent it = getIntent();

        this.timeRate = it.getIntExtra("timeRate", 250);
        //Log.d("xiiiiiiiiiiiiiiiiii", "timerate: "+this.timeRate);
        this.size = it.getIntExtra("size", 10);

        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("seed");
        if(objectArray!=null){
            seeds = new int[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                seeds[i]=(int[]) objectArray[i];
            }
        }
        adjustWindow();
        setView();
        setButton();
        if(seeds != null) {
            setSeeds();
        }

        setTraceTouch();

    }
    
    public void setSeeds() {
        Log.d("xiiiiiiiiiiiiiiiiii", "seeding ");
        for(int i=0;i<seeds.length;i++){
            staticView.grid.setBorn(seeds[i][0],seeds[i][1]);
        }
        staticView.invalidate();
    }

    public void toUserProfile(View v) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
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
                if(!restart) {
                    Log.d("xiiiiiiiiiiiiiiiiii", "restart=true ");
                    if(timeRate == 0) {
                        staticView.grid.next();
                        staticView.postInvalidate();
                    } else {
                        restart = true;
                        flag = false;
                        new Thread(new SignalLightThread()).start();
                    }
                } else {
                    Log.d("xiiiiiiiiiiiiiiiiii", "restart: ");
                    restart = false;
                    staticView.resetGrid();
                    staticView.resetData();
                }

            }
        });

    }
    public void stop(View view) {
        Log.d("xiiiiiiiiiiiiiiiiii", "stop: ");
        //Thread.currentThread().interrupt();
        flag = true;
        restart = false;
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
            while(!flag && restart){
                

                Log.d("xiiiiiiiiiiiiiiiiii", "timerate3: "+timeRate);
                try {
                    Thread.sleep(timeRate);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Log.d("Test", "I am UI");
                            borndata = (TextView)findViewById(R.id.borndata);
                            alivedata = (TextView)findViewById(R.id.livedata);
                            deaddata = (TextView)findViewById(R.id.deaddata);
                            iterdata = (TextView)findViewById(R.id.iteration);
                            emptydata = (TextView)findViewById(R.id.emptydata);
                            borndata.setText(""+ StaticView.born);
                            Log.d("Test2", String.valueOf(StaticView.born));
                            alivedata.setText(""+ StaticView.alive);
                            deaddata.setText(""+ StaticView.dead);
                            iterdata.setText(""+StaticView.iter);
                            emptydata.setText(""+ StaticView.empty);
                        }
                    });
                    //End
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                staticView.grid.next();
                staticView.postInvalidate();
            }
        }
    }
}
