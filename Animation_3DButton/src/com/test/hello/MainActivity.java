package com.test.hello;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	RenderView mRenderView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRenderView = new RenderView(this);
        setContentView(mRenderView);
    }
}