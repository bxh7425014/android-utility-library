package com.test.fonts;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public abstract class ChildView {
	LayoutParams mParams = new LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
	int mLeft = 0;
	int mTop = 0;
	int mRight = 0;
	int mBottom = 0;
	int mGravity = Gravity.CENTER;
	View mView;
	public ChildView(View v) {
		this(v, new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
	}
	public ChildView(View iv, LayoutParams params) {
		this(iv, params, 0, 0, 0, 0);
	}
	public ChildView(View iv, int left, int top, int right, int bottom) {
		this(iv, new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT), left, top, right, bottom);
	}
	public ChildView(View v, LayoutParams params, 
			int left, int top, int right, int bottom) {
		this.mView = v;
		this.mView.setLayoutParams(params);
		this.mView.setPadding(left, top, right, bottom);
	}
	public abstract void setViewRes();
	public View getView() {
		return mView;
	}
}
