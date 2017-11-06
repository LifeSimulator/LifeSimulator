package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by alyssagoncalves on 11/1/17.
 *
 * An activity that uses MyGLSurfaceView as its primary view.
 *
 * A GLSurfaceView is a specialized view where you can draw OpenGL ES graphics.
 *
 * It does not do much by itself.
 *
 * The actual drawing of objects is controlled in the GLSurfaceView.Renderer that is set on this view.
 */

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }
}