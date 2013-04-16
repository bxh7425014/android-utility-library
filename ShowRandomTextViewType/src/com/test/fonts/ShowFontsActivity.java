package com.test.fonts;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowFontsActivity extends Activity {
	private TextView mTv;
	private LinearLayout mMainLayout;
	private final int ROW_NUM = 10;
	private final int COLUME_NUM = 5;
	// 三维数组{行{列{文本颜色，背景颜色}}};
	private final int[][][] COLOR_GROUP= new int[][][]{
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
 { { Color.BLACK, Color.WHITE }, { Color.WHITE,  Color.BLACK}, {-1696157, -1647194}, {-9376218, -13407800}},
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setMainLayoutAndTextView();
//		setContentView(new SampleView(this));	// 设置Cavas绘制字体
        setCustomLayout();
    }
    
	/**
	 * 设置自定义布局及TextView
	 */
	private void setCustomLayout() {
        mMainLayout = new LinearLayout(this);
        mMainLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 加2列随机显示
        for(int index = 0; index < COLOR_GROUP[0].length + 2; index ++) {
        	// 每次添加1列文本
        	mMainLayout.addView(getTextViewLayout_Colume(index).getLayout());
        }
        setContentView(mMainLayout);
	}
	
	/**
	 * 产生随机颜色
	 * @return
	 */
	private int getRandomColor() {
  	  int red = Math.abs(new Random().nextInt()%255);
  	  int green = Math.abs(new Random().nextInt()%255);
  	  int blue = Math.abs(new Random().nextInt()%255);
  	  Log.i("bianxh", "red:" + red  + ", green: " + green + ", blue: " + blue);
  	  return Color.rgb(red, green, blue);
	}
	/**
	 * 获取竖排的布局
	 * @return
	 */
	private GTVLinearLayout getTextViewLayout_Colume(int columnIndex) {
      GTVLinearLayout layout = new GTVLinearLayout(this, LinearLayout.VERTICAL);
      for(int index = 0; index < COLOR_GROUP.length; index++) {
    	  TextView tvC1R1 = new TextView(this);
    	  int textColor;
    	  int backColor;
    	  String text;
    	  int textSize;
    	  Typeface tf = null;
    	  if (columnIndex < COLOR_GROUP[columnIndex].length) {
    		  // 获取文本颜色
    		  textColor = COLOR_GROUP[index][columnIndex][0];
    		  // 获取文本背景颜色
    		  backColor = COLOR_GROUP[index][columnIndex][1];
    		  // 获取文本
    		  text = "测试";
    		  textSize = 30;
    	  } else {
    		  textColor = getRandomColor();
    		  backColor = getRandomColor();
    		  text = "测试TC(" + textColor + "), BC(" + backColor + ")";
    		  textSize = 37;
    		  tf = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.ITALIC);
    	  }
    	  
    	  tvC1R1.setTextSize(textSize);
    	  tvC1R1.setTextColor(textColor);
    	  tvC1R1.setBackgroundColor(backColor);
    	  if (tf != null) {
    		  tvC1R1.setTypeface(tf);
    	  }
    	  layout.addChildView(null, new ChildTextView(tvC1R1, text));
//    	  layout.addChildView(null, new ChildTextView(tvC1R1, "测试"));
      }
      return layout;
	}
	
	/**
	 * 设置main.xml的布局及TextView
	 */
	private void setMainLayoutAndTextView() {
		this.getWindow().setBackgroundDrawableResource(R.drawable.bkcolor);
		setContentView(R.layout.main);
		
        mTv = (TextView)findViewById(R.id.textview);
        mTv.setTextColor(Color.BLUE);
        mTv.setTextSize(60);
        Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/ARIALI.TTF");
        mTv.setTypeface(font, Typeface.ITALIC);
        mTv.setText("asdasd好吧");
	}

	/**
	 * @author bianxh
	 * 设置Cavas绘制Text
	 */
	private static class SampleView extends View {
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private Typeface mFace, mFace1, mFace2, mFace3, mFace4, mFace5;
		Typeface font;

		public SampleView(Context context) {
			super(context);
			// 实例化自定义字体
			mFace = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/ARIAL.TTF");
			mFace1 = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/ariblk.TTF");
			mFace2 = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/ARIALBD.TTF");
			mFace3 = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/ARIALI.TTF");
			mFace4 = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/samplefont.ttf");
			font = Typeface.create("宋体", Typeface.BOLD);
			//消除锯齿     
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);     
			// 设置字体大小
			mPaint.setTextSize(45);
		}

		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.GRAY);
			// 绘制默认字体
			mPaint.setTypeface(mFace);
			canvas.drawText("asdasd打死打死的", 10, 40, mPaint);
			// 绘制自定义字体
			mPaint.setTypeface(mFace1);
			canvas.drawText("asdasd打死打死的", 10, 80, mPaint);

			mPaint.setTypeface(mFace2);
			canvas.drawText("asdasd大商店", 10, 120, mPaint);

			mPaint.setTypeface(mFace3);
			canvas.drawText("asdasd上大时代", 10, 160, mPaint);

			mPaint.setTypeface(mFace4);
			canvas.drawText("asdasd好吧", 10, 200, mPaint);

			mPaint.setTypeface(mFace5);
			canvas.drawText("asdasd颠三倒四地方", 10, 240, mPaint);
		}
	}
}