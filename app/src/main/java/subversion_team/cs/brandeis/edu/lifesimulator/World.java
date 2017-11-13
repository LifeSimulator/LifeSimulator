package subversion_team.cs.brandeis.edu.lifesimulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class World extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PainterView(this));
    }
}
