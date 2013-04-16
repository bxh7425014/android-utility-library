package com.test.galleryFocus;

import android.os.Handler;
import android.util.Log;
import android.os.Message;

public class HandleMSG {
	private Handler mHandler = null;
	private final String TAG = this.getClass().getName();
	public static final int SET_DEFAULT_FOCUS_RES = 0;
	public static final int SET_NO_FOCUS_RES = 1;
	
	public HandleMSG(Handler handler) {
		mHandler = handler;
	}
	
	/**
	 * Send message
	 * */
	public boolean SendMSG(int msgValue, int nMs) {
		Message msg = new Message();
		msg.what = msgValue;
		if (mHandler != null) {
			mHandler.sendMessageDelayed(msg, nMs);
			return true;
		} else {
			Log.i(TAG, "SendMSG::Error,mHandler is null");
			return false;
		}
	}
	/**
	 * @param msgValue
	 */
	public boolean SendMSG(int msgValue) {
		Message msg = new Message();
		msg.what = msgValue;
		if (mHandler != null) {
			mHandler.sendMessage(msg);
			return true;
		} else {
			Log.i(TAG, "SendMSG::Error,mHandler is null");
			return false;
		}
	}	
}
