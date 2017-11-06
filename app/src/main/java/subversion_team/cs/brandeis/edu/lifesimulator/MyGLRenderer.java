package subversion_team.cs.brandeis.edu.lifesimulator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by alyssagoncalves on 11/1/17.
 *
 *
 * The implementation of the GLSurfaceView.Renderer class, or renderer, within an application that
 * uses OpenGL ES is where things start to get interesting. This class controls what gets drawn on
 * the GLSurfaceView with which it is associated.
 *
 * There are three methods in a renderer that are
 * called by the Android system in order to figure out what and how to draw on a GLSurfaceView:
 *
 * onSurfaceCreated() - Called once to set up the view's OpenGL ES environment.
 *
 * onDrawFrame() - Called for each redraw of the view.
 *
 * onSurfaceChanged() - Called if the geometry of the view changes, for example when the device's screen orientation changes.
 *
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}