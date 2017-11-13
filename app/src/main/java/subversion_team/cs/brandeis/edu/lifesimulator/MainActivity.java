package subversion_team.cs.brandeis.edu.lifesimulator;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private static SeekBar timer, size;
    private static TextView timerTag, timerValue;
    private static TextView sizeTag, sizeValue;
    private static ListView configuration;
    private static Button toSimulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set all the buttons
        locateAllComponents();

        toSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        configuration = (ListView) this.findViewById(R.id.table_view);

        toSimulator = (Button) this.findViewById(R.id.click_to_simulator);
    }

    public void setTimerSeekBar() {
        timerValue.setText("Grid Refresh Rate: " + timer.getProgress() + "s" + " / " + timer.getMax() + "s");
        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        private void setSizeSeekBar() {
            sizeValue.setText("Grid Size: " + size.getProgress()*10 + " / " + size.getMax());
            size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }