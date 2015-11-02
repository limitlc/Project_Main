package com.paxw.demolist.slidingmenu.weather;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TemperatureLine extends View{

	public TemperatureLine(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
	}

	public TemperatureLine(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TemperatureLine(Context context) {
		super(context);
	}
	
	private List<Integer> list;
	private Paint paint;
	public void setData(List<Integer> list){
		this.list  = list;
		invalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawLines(pts, paint)
		
	}
	
}
