package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SimulatorActivity extends AppCompatActivity {

    Button button_start, button_save, button_stop;
    ImageButton button_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulator_page);
        //set all the buttons
        setButtons();
    }

    private void setButtons() {
        //set three buttons
        button_save = (Button) findViewById(R.id.id_save);
        button_start = (Button) findViewById(R.id.id_start);
        button_stop = (Button) findViewById(R.id.id_stop);
        button_user_profile = (ImageButton) findViewById(R.id.id_user_profile);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_start:
                start_game(v);
                break;
            case R.id.id_stop:
                //stop_game(v);
                break;
            case R.id.id_save:
                //save_pic(v);
                break;
            case R.id.id_user_profile:
                goto_userprofile(v);
                break;
        }
    }

    //send intent to start game
    public void start_game(View view) {
        Intent intent = new Intent(this, World.class);
        startActivity(intent);
        startActivityForResult(intent,1);
    }

    //send intent to stop game
//    public void stop_game(View view)
//    {
//
//    }
//
//    //send intent to save game
//    public void save_pic(View view)
//    {
//
//    }

    //send intent to user profile
    public void goto_userprofile(View view)
    {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }


    //get results back and dispaly it
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
//                Toast.makeText(this, "The last conversion was: "+result, Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
