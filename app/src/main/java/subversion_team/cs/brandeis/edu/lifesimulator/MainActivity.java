package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startOpenGL(View view) {
        Intent intent = new Intent(this, OpenGLES20Activity.class);
        startActivity(intent);
    }
}
