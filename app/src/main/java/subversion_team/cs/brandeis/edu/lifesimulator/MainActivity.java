package subversion_team.cs.brandeis.edu.lifesimulator;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static SeekBar timer, size;
    private static TextView timerTag, timerValue;
    private static TextView sizeTag, sizeValue;
    private static Button toSimulator;
    ImageButton glider, blinker, gun;
    int[][] glider_seed, blinker_seed, gun_seed;

    DatabaseHelper helper;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set all the buttons
        glider = (ImageButton)findViewById(R.id.id_glider);
        glider_seed = new int[][]{{1, 2}, {2, 3}, {3, 1}, {3, 2}, {3, 3}};
        blinker = (ImageButton)findViewById(R.id.id_blinker);
        blinker_seed = new int[][]{{1, 5}, {2, 5}, {3, 5}, {5, 1}, {5, 2}, {5, 3}, {5, 7}, {5, 8}, {5, 9}, {7, 5}, {8, 5}, {9, 5}};
        gun = (ImageButton)findViewById(R.id.id_gun);
        gun_seed = new int[][]{{1, 25}, {2, 23}, {2, 25}, {3, 13}, {3, 14}, {3, 21}, {3,22}, {3, 35}, {3,36},{4, 12},{4, 16},
                {4,21},{4,22},{4,35},{4,36},{5,1},{5,2},{5,11},{5,17},{5,21},{5,22},{6,1},{6,2},{6,11},{6,15},{6,17},{6,18},{6,23},
                {6,25},{7,11},{7,17},{7,25},{8, 12},{8,16},{9, 13},{9, 14}};
        locateAllComponents();

        helper = new DatabaseHelper(this);
        userEmail = helper.getCurrentUserEmail();

        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                timerValue.setText("Grid Refresh Rate: " + timer.getProgress() + "times/s");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sizeValue.setText("Grid Size: " + size.getProgress()*5 + " / " + size.getMax()*5);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        toSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), World.class);
                if(timer.getProgress()==0) {
                    it.putExtra("timeRate", 0);
                } else {
                    it.putExtra("timeRate", 500/timer.getProgress());
                }
                it.putExtra("size", size.getProgress()*5);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("seed", null);
                it.putExtras(mBundle);
                startActivity(it);
            }
        });

    }

    private void locateAllComponents() {
        timer = (SeekBar) this.findViewById(R.id.refresh_rate_slider);
        size = (SeekBar) this.findViewById(R.id.grid_size_slider);

        timerTag = (TextView) this.findViewById(R.id.timer_rate_text);
        timerValue = (TextView) this.findViewById(R.id.timer_value);

        sizeTag = (TextView) this.findViewById(R.id.grid_size_text);
        sizeValue = (TextView) this.findViewById(R.id.size_value);

        toSimulator = (Button) this.findViewById(R.id.click_to_simulator);
    }

    public void glider(View v) {
        Log.d("xiiiiiiiiiiiiiiiiii", "in glider");
        Intent it = new Intent(getApplicationContext(), World.class);
        it.putExtra("timeRate", 125);
        it.putExtra("size", 20);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("seed", glider_seed);
        it.putExtras(mBundle);
        Log.d("xiiiiiiiiiiiiiiiiii", Arrays.deepToString(glider_seed));

        helper.addAchievement(DatabaseHelper.a4Name, userEmail);
        Log.d("SEED", "User " + userEmail + " earned " + DatabaseHelper.a4Name);

        startActivity(it);
    }
    public void blinker(View v) {
        Log.d("xiiiiiiiiiiiiiiiiii", "in blinker");
        Intent it = new Intent(getApplicationContext(), World.class);
        it.putExtra("timeRate", 125);
        it.putExtra("size", 11);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("seed", blinker_seed);
        it.putExtras(mBundle);
        Log.d("xiiiiiiiiiiiiiiiiii", Arrays.deepToString(blinker_seed));

        helper.addAchievement(DatabaseHelper.a3Name, userEmail);
        Log.d("SEED", "User " + userEmail + " earned " + DatabaseHelper.a3Name);

        startActivity(it);
    }
    public void gun(View v) {
        Log.d("xiiiiiiiiiiiiiiiiii", "in blinker");
        Intent it = new Intent(getApplicationContext(), World.class);
        it.putExtra("timeRate", 125);
        it.putExtra("size", 40);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("seed", gun_seed);
        it.putExtras(mBundle);
        Log.d("xiiiiiiiiiiiiiiiiii", Arrays.deepToString(gun_seed));

        helper.addAchievement(DatabaseHelper.a5Name, userEmail);
        Log.d("SEED", "User " + userEmail + " earned " + DatabaseHelper.a5Name);

        startActivity(it);
    }

}