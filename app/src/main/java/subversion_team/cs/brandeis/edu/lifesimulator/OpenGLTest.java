package subversion_team.cs.brandeis.edu.lifesimulator;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLTest extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a GLSurfaceView instance and set it as the ContentView for this Activity.
        mGLView = new AlyssaGLSurfaceView(this);
        setContentView(mGLView);
    }
}
