package com.test.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class SingleRowPosterModel {
	private final int INT_BYTES = 4;
	private float[] mVertices = {
	// Vertices
			-1.5f, 2.0f, 0, //
			1.5f, 2.0f, 0, //
			-1.5f, -2.0f, 0, //
			1.5f, -2.0f, 0, //
	};
	private float[] mTexCoords = {
	// TexCoords
			0, 0, //
			1, 0, //
			0, 1, //
			1, 1, //
	};
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mTexCoordBuffer;
	public SingleRowPosterModel()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mVertices.length * INT_BYTES);
		byteBuffer.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuffer.asFloatBuffer();
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

		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
