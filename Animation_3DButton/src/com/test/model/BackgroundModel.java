package com.test.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BackgroundModel {
//	public static final int INT_BYTES = 4;
	private final int INT_BYTES = 4;
//	private static final int ONE = 0x10000;
	private final int ONE = 0x10000;

	private int[] mVertices = {
	// Vertices
			-16 * ONE, 9 * ONE, 0, //
			16 * ONE, 9 * ONE, 0, //
			-16 * ONE, -9 * ONE, 0, //
			16 * ONE, -9 * ONE, 0, //
	};

	private float[] mTexCoords = {
	// TexCoords
			0, 0, // 
			1, 0, //
			0, 1, // 
			1, 1, //
	};

	private IntBuffer mVertexBuffer;
	private FloatBuffer mTexCoordBuffer;

	public BackgroundModel()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mVertices.length * INT_BYTES);
		byteBuffer.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuffer.asIntBuffer();
		mVertexBuffer.put(mVertices);
		mVertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(mTexCoords.length * INT_BYTES);
		byteBuffer.order(ByteOrder.nativeOrder());
		mTexCoordBuffer = byteBuffer.asFloatBuffer();
		mTexCoordBuffer.put(mTexCoords);
		mTexCoordBuffer.position(0);
	}

	public void draw(GL10 gl, int texture)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
