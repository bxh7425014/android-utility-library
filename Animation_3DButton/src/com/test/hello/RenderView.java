package com.test.hello;

import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.test.model.BackgroundModel;
import com.test.model.ButtonModel;
import com.test.model.DoubleRowPosterModel;
import com.test.model.SingleRowPosterModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public final class RenderView extends GLSurfaceView implements GLSurfaceView.Renderer {
	// public class RenderView implements GLSurfaceView.Renderer {
	int texture = -1;
	private Context mContext = null;
	public static final int INT_BYTES = 4;
	private BackgroundModel mBackground;
	private ButtonModel mButton;
	private SingleRowPosterModel mSingleRowPoster;
	private DoubleRowPosterModel mDoubleRowPoster;
	// Animation ID
	public static final int AM_Button_Clockwise = 0;
	public static final int AM_Button_Anticlockwise = 1;
	public static final int AM_SingleRowPoster_Clockwise = 2;
	public static final int AM_SingleRowPoster_Anticlockwise = 3;
	public static final int AM_DoubleRowPoster_Clockwise = 4;
	public static final int AM_DoubleRowPoster_Anticlockwise = 5;
	public static final int AM_Render_One_Frame = 6;
	public static final int AM_Render_Stop = 7;
	public static int AnimationID = AM_Button_Anticlockwise;
	private float Button_Rotate_Size = 0;
	
	public RenderView(final Context context) {
		super(context);
		mContext = context;
//		setBackgroundDrawable(null);
//		setFocusable(true);
//		setEGLConfigChooser(true);
		setRenderer(this);
		mBackground = new BackgroundModel();
		mButton = new ButtonModel();
		mSingleRowPoster = new SingleRowPosterModel();
		mDoubleRowPoster = new DoubleRowPosterModel();
	}

//	int test  = 0;
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 清除屏幕和深度缓冲
		gl.glLoadIdentity(); // 重置当前的模型观察矩阵
		drawBackground(gl);
//		test ++;
//		test--;
//		drawSingleRowPoster(gl);
		
		drawButton(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 22, ratio, 1, 100);
//		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 15);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0, 0, 0, 0);	// Clear screen
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);		// 精细的透视修正
		gl.glShadeModel(GL10.GL_SMOOTH);		// 启用阴影平滑
		gl.glEnable(GL10.GL_DEPTH_TEST);		// 启用深度测试
		gl.glClearDepthf(1.0f);		// 启用纹理映射
		gl.glEnable(GL10.GL_TEXTURE_2D);		// 允许2D贴图,纹理
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		loadBackgroundTexture(gl);
		loadButtonTexture(gl);
		loadSingleRowPosterTexture(gl);
		loadDoubleRowPosterTexture(gl);
	}

	private int mBackgroundTexture;
	private void loadBackgroundTexture(GL10 gl)
	{
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		mBackgroundTexture = intBuffer.get();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mBackgroundTexture);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.background);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
	private void drawBackground(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -46.0f);
		mBackground.draw(gl, mBackgroundTexture);
		gl.glPopMatrix();
	}
	
	private int mButtonTexture;
	private void loadButtonTexture(GL10 gl)
	{
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		mButtonTexture = intBuffer.get();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mButtonTexture);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.button_focus);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
//	private final float Button_Radius = 22.0f;
	private final float Button_Radius = 40.0f;
	private int[] DrawButtonGroup = { -4, 4, -3, 3, -2, 2, -1, 1, 0 };
	private void drawButton(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -6.85f, -40.0f);
		switch (AnimationID) {
		case AM_Button_Clockwise:
			Button_Rotate_Size += 0.1f;
			if (Button_Rotate_Size > 10) {
				Button_Rotate_Size = 0;
				AnimationID = AM_Render_One_Frame;
			}
			break;
		case AM_Button_Anticlockwise:
			Button_Rotate_Size -= 0.1f;
			if (Button_Rotate_Size < -10) {
				Button_Rotate_Size = 0;
				AnimationID = AM_Render_One_Frame;
			}
			break;
		case AM_Render_One_Frame:
			AnimationID = AM_Render_Stop;
			break;
		case AM_Render_Stop:
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			break;
		default:
			break;
		}
		
//		for (int index = 0;index < DrawButtonGroup.length;index ++) {
//			gl.glPushMatrix();
//			gl.glTranslatef(0.0f, 0.0f, -Button_Radius);
//			gl.glRotatef(-DrawButtonGroup[index]*10 -Button_Rotate_Size, 0.0f, 1.0f, 0.0f);
//			gl.glTranslatef(0.0f, 0.0f, Button_Radius);
//			gl.glRotatef(DrawButtonGroup[index] * 10 + Button_Rotate_Size, 0.0f, 1.0f, 0.0f);
//			mButton.draw(gl, mButtonTexture);
//			gl.glPopMatrix();
//		}
		//test code
		for (int index = 0;index < DrawButtonGroup.length;index ++) {
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, 0.0f, -Button_Radius);
			gl.glRotatef(-DrawButtonGroup[index]* 4.5f -Button_Rotate_Size, 0.0f, 1.0f, 0.0f);
			gl.glTranslatef(0.0f, 0.0f, Button_Radius);
			gl.glRotatef(DrawButtonGroup[index] * 4.5f + Button_Rotate_Size, 0.0f, 1.0f, 0.0f);
			mButton.draw(gl, mButtonTexture);
			gl.glPopMatrix();
		}
		
		gl.glPopMatrix();
	}

	private int mSingleRowPosterTexture;
	private void loadSingleRowPosterTexture(GL10 gl)
	{
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		mSingleRowPosterTexture = intBuffer.get();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mSingleRowPosterTexture);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
	private void drawSingleRowPoster(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -40.0f);
		
		gl.glPushMatrix();
		gl.glRotatef(Button_Rotate_Size, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -10.0f);
		mSingleRowPoster.draw(gl, mSingleRowPosterTexture);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	
	private int mDoubleRowPosterTexture;
	private void loadDoubleRowPosterTexture(GL10 gl)
	{
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		mDoubleRowPosterTexture = intBuffer.get();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mDoubleRowPosterTexture);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
	private void drawDoubleRowPoster(GL10 gl,int focusItem) {
	}
}
