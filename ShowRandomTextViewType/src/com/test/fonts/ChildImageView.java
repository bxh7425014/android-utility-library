package com.test.fonts;

import android.widget.ImageView;

public class ChildImageView extends ChildView{
	private int mId;
	
	public ChildImageView(ImageView v, int drawableID) {
		super(v);
		mId = drawableID;
	}
	
	public void setDrawable(int id) {
		mId = id;
	}
	@Override
	public void setViewRes() {
		if (mView instanceof ImageView) {
			ImageView iv = (ImageView)mView;
			iv.setImageResource(mId);		
		}
	}
}
