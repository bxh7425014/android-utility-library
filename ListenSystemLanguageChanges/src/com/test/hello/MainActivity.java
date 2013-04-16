package com.test.hello;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends Activity {
	private IntentFilter mIntentFilter;
	private Receiver mReceiver;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LogExt.LogD(this, Thread.currentThread().getStackTrace());
        mIntentFilter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        mReceiver = new Receiver();
        this.registerReceiver(mReceiver, mIntentFilter);
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
		this.unregisterReceiver(mReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		LogExt.LogD(this, Thread.currentThread().getStackTrace());
	}
	
	public class Receiver extends  BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			LogExt.LogD(this, Thread.currentThread().getStackTrace(), intent.getAction());
		}
		
	}
}
// 系统设置启动命令：am start -n com.tcl.mstar.settings/.TclSettings