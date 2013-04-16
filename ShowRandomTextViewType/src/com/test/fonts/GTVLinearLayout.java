package com.test.fonts;

import java.util.HashMap;
import java.util.Iterator;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class GTVLinearLayout {
	private static GTVLinearLayout mInst;
	private LinearLayout mLayout;
	private HashMap<String, ChildView> mImageViewMap = new HashMap<String, ChildView>(); 
	private Context mContext;
	
	public GTVLinearLayout(Context ctx) {
		this(ctx, LinearLayout.HORIZONTAL);
	}
	
	public GTVLinearLayout(Context ctx, int orientation) {
		this(ctx, orientation, 0, 0, 0, 0);
	}
	
	public GTVLinearLayout(Context ctx, int orientation, int left, int top, int right, int bottom) {
		this(ctx, orientation, left, top, right, bottom, Gravity.LEFT);
	}
	
	public GTVLinearLayout(Context ctx, int orientation, int gravity) {
		this(ctx, orientation, 0, 0, 0, 0, gravity);
	}
	
	public GTVLinearLayout(Context ctx, int orientation, int left, int top, int right, int bottom, int gravity) {
		mContext = ctx;
		mLayout = new LinearLayout(mContext);
		mLayout.setOrientation(orientation);
		mLayout.setPadding(left, top, right, bottom);
		mLayout.setGravity(gravity);
	}
	
	public LinearLayout getLayout() {
		return mLayout;
	}
	
	public void addChildView(String str, ChildView ii) {
		mImageViewMap.put(str, ii);
		mLayout.addView(ii.getView(),ii.getView().getLayoutParams());
		refreshChildView(str);
	}
	
	public ChildView getChildViewContent(String key) {
		return mImageViewMap.get(key); 
	}
	
	public void removeAll() {
		for(Iterator it = mImageViewMap.keySet().iterator(); it.hasNext();) {
			String str = (String) it.next();
			removeChildView(mImageViewMap.get(str).getView());
		}
		mImageViewMap.clear();
	}
	
	public void removeChildView(String str) {
		removeChildView(mImageViewMap.get(str).getView());
		mImageViewMap.remove(str);
	}
	
	private void removeChildView(View iv) {
		mLayout.removeView(iv);
	}
	
	public void refreshAllChildView() {
		if (mImageViewMap == null) {
			return;
		}
		for(Iterator it = mImageViewMap.keySet().iterator(); it.hasNext();) {
			String str = (String) it.next();
			refreshChildView(str);
		}
	}
	
	public void refreshChildView(String str) {
		mImageViewMap.get(str).setViewRes();
	}
}
