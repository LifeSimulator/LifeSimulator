package subversion_team.cs.brandeis.edu.lifesimulator;

import android.opengl.GLSurfaceView;
import android.content.Context;

/**
 * Created by alyssagoncalves on 11/6/17.
 *
 * The SurfaceView for OpenGLTest.
 */

public class AlyssaGLSurfaceView extends GLSurfaceView {
    private final AlyssaGLRenderer mRenderer;

    public AlyssaGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new AlyssaGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }
}