package com.test.hello;


import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private GLRenderView mGLRenderView = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLRenderView = new GLRenderView(this);
        setContentView(mGLRenderView);
    }
}