package com.test.fonts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FontsDisplayTestActivity extends Activity {
    TextView mTv;
    TextView mTvSize;
    Button mBtnZoomIn;
    Button mBtnZoomOut;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTv = (TextView)findViewById(R.id.tv);
        mTvSize = (TextView)findViewById(R.id.tv_size);
        mBtnZoomIn = (Button)findViewById(R.id.zoom_in);
        mBtnZoomOut = (Button)findViewById(R.id.zoom_out);
        mBtnZoomIn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mTv.setTextSize(mTv.getTextSize() + 1);		
				mTvSize.setText("" + mTv.getTextSize());
			}
        });
        mBtnZoomOut.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mTv.setTextSize(mTv.getTextSize() - 1);		
				mTvSize.setText("" + mTv.getTextSize());
			}
        });
    }
}