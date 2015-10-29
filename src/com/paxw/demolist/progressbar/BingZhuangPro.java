package com.paxw.demolist.progressbar;


import com.paxw.demolist.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.PorterDuff;
public class BingZhuangPro extends View {
	
	/**RectF 这个类包含一个矩形的四个单精度浮点坐标。
	矩形通过上下左右4个边的坐标来表示一个矩形。
	这些坐标值属性可以被直接访问，用width（）和
　　height（）方法可以获取矩形的宽和高。
	注意：大多数方法不会检查这些坐标分类是否错误（也就是left<=right和top& lt;=bottom）.
	RectF一共有四个构造方法：
　　RectF（）构造一个无参的矩形
	RectF（float left,float top,float right,float bottom）构造一个指定了4个参数的矩形
	RectF(Rect F r)根据指定的RectF对象来构造一个RectF对象(对象的左边坐标不变) 
	RectF(Rect r)根据给定的Rect对象来构造一个RectF对象
	*/
	private final RectF mRect = new RectF();
	//private final RectF mRectInner = new RectF();
	//前后背景的画笔
	
	private final Paint mPaintForeground = new Paint();
	private final Paint mPaintBackground = new Paint();
	//前景色和背景色
	private int mColorForeground = Color.WHITE;
	private int mColorBackground = Color.BLACK;
	//记录百分比
	private int mProgress;
	/**
	 * Value which makes our custom drawn indicator have roughly the same size
	 * as the built-in ProgressBar indicator. Unit: dp
	 */
	private static final float PADDING = 4;
	private float mPadding;
	private Bitmap mBitmap;
	/**
	 * Value which makes our custom drawn indicator have roughly the same
	 * thickness as the built-in ProgressBar indicator. Expressed as the ration
	 * between the inner and outer radiuses
	 */
	private static final float INNER_RADIUS_RATIO = 0.84f;

	public BingZhuangPro(Context context) {
		this(context, null);
	}

	public BingZhuangPro(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.PieProgress));
		
		Resources r = context.getResources();
		float scale = r.getDisplayMetrics().density;
		mPadding = scale * PADDING + 2;
		mPaintForeground.setColor(mColorForeground);
		mPaintForeground.setAntiAlias(true);
		mPaintBackground.setColor(mColorBackground);
		mPaintBackground.setAntiAlias(true);
	}
	
	/**
	 * Parse the attributes passed to the view from the XML
	 * 
	 * @param a
	 *            the attributes to parse
	 */
	private void parseAttributes(TypedArray a) {
		mColorForeground = a.getColor(R.styleable.PieProgress_foregroundColor, mColorForeground);
		mColorBackground = a.getColor(R.styleable.PieProgress_backgroundColor, mColorBackground);
		// Recycle
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2
				- mBitmap.getHeight() / 2, null);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		float bitmapWidth = w - 2 * mPadding;
		float bitmapHeight = h - 2 * mPadding;
		float radius = Math.min(bitmapWidth / 2, bitmapHeight / 2);
		mRect.set(0, 0, bitmapWidth, bitmapHeight);
		radius *= INNER_RADIUS_RATIO;
//		mRectInner.set(bitmapWidth / 2f - radius, bitmapHeight / 2f - radius, bitmapWidth / 2f
//				+ radius, bitmapHeight / 2f + radius);
		updateBitmap();
	}

	/**
	 * Set the foreground color for this indicator. The foreground is the part
	 * of the indicator that shows the actual progress
	 */
	public void setForegroundColor(int color) {
		this.mColorForeground = color;
		mPaintForeground.setColor(color);
		invalidate();
	}

	/**
	 * Set the background color for this indicator. The background is a dim and
	 * subtle part of the indicator that appears below the actual progress
	 */
	public void setBackgroundColor(int color) {
		this.mColorBackground = color;
		mPaintBackground.setColor(color);
		invalidate();
	}

	/**
	 * @param progress
	 *            A number between 0 and 360
	 */
	public synchronized void setProgress(int progress) {
		mProgress = progress;
		if (progress > 360) {
			mProgress = 360;
		}
		updateBitmap();
	}
	
	public void reset() {
		mProgress = 0;
		updateBitmap();
	}

	private void updateBitmap() {
		if (mRect == null || mRect.width() == 0) {
			return;
		}
		mBitmap = Bitmap.createBitmap((int) mRect.width(), (int) mRect.height(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		canvas.drawArc(mRect, -90, 360, true, mPaintBackground);
		if (mProgress < 3) {
			canvas.drawLine(mRect.width() / 2, mRect.height() / 2, mRect.width() / 2, 0,
					mPaintForeground);
		}
		canvas.drawArc(mRect, -90, mProgress, true, mPaintForeground);
//		postInvalidate();
		invalidate();
	}

}
