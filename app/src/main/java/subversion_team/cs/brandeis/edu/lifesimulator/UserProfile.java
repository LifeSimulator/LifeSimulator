package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by xichen on 11/1/17.
 */

public class UserProfile extends AppCompatActivity {

    public String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
    }

    public void startAchievements(View view) {
        Log.d("Launched Achievements","Success");
        Intent intent = new Intent(this, Achievements.class);
        startActivity(intent);
    }
}
