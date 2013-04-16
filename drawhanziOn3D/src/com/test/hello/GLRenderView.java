package com.test.hello;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

public final class GLRenderView extends GLSurfaceView implements Renderer {
    private int mWidth;
    private int mHeight;
    private int mTextureID;
    private LabelMaker mLabels;
    private Paint mLabelPaint;
    private int mLabelA;
    private int mLabelB;
    Grid mGrid;
	public GLRenderView(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setFocusable(true);
		setEGLConfigChooser(true);
		setRenderer(this);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		/*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        gl.glDisable(GL10.GL_DITHER);

        /*
         * Usually, the first thing one might want to do is to clear
         * the screen. The most efficient way of doing this is to use
         * glClear().
         */
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        mLabels.beginDrawing(gl, mWidth, mHeight);
        gl.glRotatef(angle, 0, 0, 1.0f);
        mLabels.draw(gl, 200, 100, mLabelA);
        mLabels.draw(gl, 200, 500, mLabelB);
        mLabels.endDrawing(gl);
        
        mGrid.draw(gl, true);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		mWidth = w;
		mHeight = h;
		gl.glViewport(0, 0, w, h);

		/*
		 * Set our projection matrix. This doesn't have to be done each time we
		 * draw, but usually a new projection needs to be set when the viewport
		 * is resized.
		 */

		float ratio = (float) w / h;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        gl.glDisable(GL10.GL_DITHER);

        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_FASTEST);

        gl.glClearColor(.5f, .5f, .5f, 1);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        /*
         * Create our texture. This has to be done each time the
         * surface is created.
         */

        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);

        mTextureID = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_CLAMP_TO_EDGE);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
                GL10.GL_REPLACE);

        mLabelPaint = new Paint();
        mLabelPaint.setTextSize(32);
        mLabelPaint.setAntiAlias(true);
        mLabelPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        if (mLabels != null) {
            mLabels.shutdown(gl);
        } else {
            mLabels = new LabelMaker(true, 256, 64);
        }
        mLabels.initialize(gl);
        mLabels.beginAdding(gl);
        mLabelA = mLabels.add(gl, "A:测试汉字1", mLabelPaint);
        mLabelB = mLabels.add(gl, "B", mLabelPaint);
        mLabels.endAdding(gl);
        
        mGrid = new Grid(2, 2);

		mGrid.set(0, 0, 0.0f, 0.0f, 0.0f, 0, 0.40625f);
		mGrid.set(1, 0, 19, 0.0f, 0.0f, 0.07421875f, 0.40625f);
		mGrid.set(0, 1, 0.0f, 38, 0.0f, 0, 1);
		mGrid.set(1, 1, 19, 38, 0.0f, 0.07421875f, 1);
	}
}
