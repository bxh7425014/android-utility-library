package com.test.hello;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GetAppInfoActivity extends ListActivity {
	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	LayoutInflater layoutInflater;
	private AppAdapter mAppAdapter;
	ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getAllAppInfo_first();
		getAllAppInfo_second();
		List<View> viewList = new ArrayList<View>();
		viewList.add(getLayoutInflater().inflate(R.layout.main, null));
		mAppAdapter = new AppAdapter(this);
		setListAdapter(mAppAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		LogExt.LogD(this, Thread.currentThread().getStackTrace(),
				"select item = " + position);
		PackageManager packageManager = getPackageManager();
		Intent intent = new Intent();
		intent = packageManager.getLaunchIntentForPackage(appList.get(position).packageName);
		if (intent != null) {
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException anf) {
				LogExt.LogE(this, Thread.currentThread().getStackTrace(), null,
						anf);
			}
		}
	}

	/**
	 * 第一种获取已安装应用的方式
	 */
	private void getAllAppInfo_first() {
		PackageManager pm = getPackageManager();
		List<PackageInfo> packages = pm
//				.getInstalledPackages(0);
				.getInstalledPackages(PackageManager.MATCH_DEFAULT_ONLY);
		LogExt.LogD(this, Thread.currentThread().getStackTrace(),
				"packages.size() = " + packages.size());
		appList.clear();
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(
					getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo
					.loadIcon(getPackageManager());
			appList.add(tmpInfo);
			LogExt.LogD(this, Thread.currentThread().getStackTrace(), ""
					+ tmpInfo);
		}
	}
	
	private void getAllAppInfo_second() {
		PackageManager pm = getPackageManager(); // 获得PackageManager对象
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = pm
				.queryIntentActivities(mainIntent, 0);
		appList.clear();
		for (ResolveInfo reInfo : resolveInfos) {
			// 可能一个包有多个图标
			LogExt.LogD(this, Thread.currentThread().getStackTrace(),
					"reInfo.activityInfo.name=" + reInfo.activityInfo.name);
			String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
			String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
			Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
			// 创建一个AppInfo对象，并赋值
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = appLabel;
			tmpInfo.packageName = reInfo.activityInfo.packageName;
			tmpInfo.mainActivityName = activityName;
			tmpInfo.appIcon = icon;
			appList.add(tmpInfo);
			LogExt.LogD(this, Thread.currentThread().getStackTrace(), ""
					+ tmpInfo);
		}
	}
	

	private class AppAdapter extends BaseAdapter {
		public AppAdapter(Context context) {
			layoutInflater = (LayoutInflater) context
					.getSystemService(inflater);
		}

		@Override
		public int getCount() {
			return appList.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
					R.layout.main, null);
			ImageView ivLogo = (ImageView) linearLayout
					.findViewById(R.id.ivLogo);
			TextView tvApplicationName = ((TextView) linearLayout
					.findViewById(R.id.tvApplicationName));
			ivLogo.setImageDrawable(appList.get(position).appIcon);
			tvApplicationName.setText("appPosion(" + (position + 1) + "/"
					+ appList.size() + ")" + appList.get(position));
			return linearLayout;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}
	}

	public class AppInfo {
		public String appName = "";
		public String packageName = "";
		public String mainActivityName = "";
		public String versionName = "";
		public int versionCode = 0;
		public Drawable appIcon = null;

		public String toString() {
			return "{app: " + appName + ", package: " + packageName
					+ ", mainActivityName: " + mainActivityName
					+ ", version: " + versionName + ", versionCode:"
					+ versionCode + "}";
		}
	}

}