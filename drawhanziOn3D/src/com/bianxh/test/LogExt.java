package com.bianxh.test;
import android.util.Log;

public class LogExt {
	private static final String TAG="LogExt";
	public static void Log(Object obj, String strInfo){
		if( obj == null )
			Log.e(TAG, "Input Class Error(null)");
		else
			LogExt.Log(obj.getClass(), strInfo);
	}
	@SuppressWarnings("unchecked")
	public static void Log(Class cls, String strInfo){
		if(!CanDebug())
			return ;
		
		if( cls == null )
			Log.e(TAG, "Input Class Error(null)");
		else
			Log.e(TAG, cls.toString()+"::"+strInfo);
	}
	private static boolean CanDebug(){return bEnableDebug;};
	private static boolean bEnableDebug = true;
}
