package subversion_team.cs.brandeis.edu.lifesimulator;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import android.opengl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by alyssagoncalves on 11/6/17.
 *
 * The Renderer for OpenGLTest.
 */

public class AlyssaGLRenderer implements GLSurfaceView.Renderer {

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eglConfig) {

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}
