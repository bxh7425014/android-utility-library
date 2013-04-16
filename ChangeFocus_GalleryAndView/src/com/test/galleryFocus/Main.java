package com.test.galleryFocus;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class Main extends Activity
{
  private Gallery myGallery01;
  private int itemSelect = 0;
  private myInternetGalleryAdapter mGalleryAdapter;
  private Bitmap mTestBmp;
  private ImageView mLastIV;
  private ImageView mCurrentIV;

  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message msg)
    {
      super.handleMessage(msg);
      switch (msg.what)
      {
        case HandleMSG.SET_DEFAULT_FOCUS_RES:
          if (mLastIV != null) {
            Logger.Log(this, Thread.currentThread().getStackTrace(),"setLast to default:" + mLastIV);
            mLastIV.setBackgroundResource(R.drawable.gallery_item);
          }
          if (mCurrentIV != null) {
            Logger.Log(this, Thread.currentThread().getStackTrace(),"setCurrent to default:" + mCurrentIV);
            mCurrentIV.setBackgroundResource(R.drawable.gallery_item);
          }
          return;
        case HandleMSG.SET_NO_FOCUS_RES:
          if (mLastIV != null) {
            Logger.Log(this, Thread.currentThread().getStackTrace(),"setLast to no focus res:" + mLastIV);
            mLastIV.setBackgroundResource(R.drawable.gallery_item1);
          }
          return;
      }
    }
  };
  private String[] myImageURL = new String[]
  {
      "http://www.techsupportalert.com/files/images/icons/icon_internet.png",
      "http://www.techsupportalert.com/files/images/icons/icon_internet.png",
      "http://www.techsupportalert.com/files/images/icons/icon_internet.png",
      "http://www.techsupportalert.com/files/images/icons/icon_internet.png",
      "http://www.techsupportalert.com/files/images/icons/icon_internet.png" };

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mTestBmp = BitmapFactory.decodeResource(getResources(),
        R.drawable.icon);
    myGallery01 = (Gallery) findViewById(R.id.myGallery01);
    mGalleryAdapter = new myInternetGalleryAdapter(this);
    myGallery01.setAdapter(mGalleryAdapter);
    myGallery01.setOnItemSelectedListener(new OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> arg0,
              View arg1, int arg2, long arg3)
          {
            Logger.Log(this, Thread.currentThread().getStackTrace(),"arg2 = " + arg2 + ", arg1 = " + arg1);
            itemSelect = arg2;
            mCurrentIV = (ImageView) arg1;
            new HandleMSG(mHandler).SendMSG(HandleMSG.SET_DEFAULT_FOCUS_RES);
            arg1.setOnFocusChangeListener(new OnFocusChangeListener() {
              public void onFocusChange(View v, boolean hasFocus) {
                Logger.Log(this, Thread.currentThread().getStackTrace(),"arg1.onFocusChange(), v = " + v
                    + ", hasFocus = " + hasFocus);
                if (!hasFocus) {
                  mLastIV = (ImageView) v;
                  new HandleMSG(mHandler).SendMSG(HandleMSG.SET_NO_FOCUS_RES);
                } else {
                  new HandleMSG(mHandler).SendMSG(HandleMSG.SET_DEFAULT_FOCUS_RES);      
                }
              }
            });
          }

          @Override
          public void onNothingSelected(AdapterView<?> arg0)
          {
            Logger.Log(this, Thread.currentThread().getStackTrace(), "arg0 = " + arg0);
          }

        });
    myGallery01
        .setOnFocusChangeListener(new OnFocusChangeListener()
        {
          public void onFocusChange(View v, boolean hasFocus)
          {
            Logger.Log(this, Thread.currentThread().getStackTrace(), "hasFocus = "
                + hasFocus + ", v = " + v);
          }
        });
  }

  private Bitmap getBitmap(String str)
  {
    try
    {
      URL aryURI = new URL(str);
      URLConnection conn = aryURI.openConnection();
      conn.connect();
      InputStream is = conn.getInputStream();
      Bitmap bm = BitmapFactory.decodeStream(is);
      is.close();
      return bm;
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public class myInternetGalleryAdapter extends BaseAdapter
  {
    private Context myContext;
    private int mGalleryItemBackground;
    public myInternetGalleryAdapter(Context c)
    {
      this.myContext = c;
      TypedArray a = myContext
          .obtainStyledAttributes(R.styleable.Gallery);
      mGalleryItemBackground = a.getResourceId(
          R.styleable.Gallery_android_galleryItemBackground, 0);
      a.recycle();
    }
    public int getCount()
    {
      return myImageURL.length;
    }

    public Object getItem(int position)
    {
      return position;
    }

    public long getItemId(int position)
    {
      return position;
    }
    public float getScale(boolean focused, int offset)
    {
      /* Formula: 1 / (2 ^ offset) */
      return Math.max(0,
          1.0f / (float) Math.pow(2, Math.abs(offset)));
    }

    @Override
    public View getView(final int position, View convertView,
        ViewGroup parent)
    {
      ImageView imageView = new ImageView(this.myContext);
      imageView.setImageBitmap(mTestBmp);

      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));
      imageView.setBackgroundResource(mGalleryItemBackground);

//      Logger.Log(this, Thread.currentThread().getStackTrace(), "position = " + position + ", itemSelect = "
//          + itemSelect);
      // if (position == itemSelect) {
      imageView.setBackgroundResource(R.drawable.gallery_item);
      // }
      return imageView;
    }
  }
}