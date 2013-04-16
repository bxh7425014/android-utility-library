package com.test.fonts;

import android.widget.TextView;

public class ChildTextView extends ChildView {
	private String mContent;
	private int mTextSize = -1;
	
	public ChildTextView(TextView v, String str) {
		super(v);
		mContent = str;
	}
	
	public ChildTextView(TextView v, String str, int textSize) {
		this(v, str);
		mTextSize = textSize;
	}

	public void setString(String str) {
		mContent = str;
	}
	
	public void setTextSize(int size) {
		mTextSize = size;
	}
	
	@Override
	public void setViewRes() {
		if (mView instanceof TextView) {
			TextView tv = (TextView)mView;
			tv.setText(mContent);
			if (mTextSize != -1) {
				tv.setTextSize(mTextSize);
			}
		}
	}
}
