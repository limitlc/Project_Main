package com.paxw.demolist.anim;


import com.paxw.demolist.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class ValueAnimatorActivity extends Activity
{
	protected static final String TAG = "MainActivity";

	private ImageView mBlueBall;

	private float mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mBlueBall = (ImageView) findViewById(R.id.id_ball);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

	}

	/**
	 * 自由落体
	 * 
	 * @param view
	 */
	public void verticalRun(View view){
		ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
				- mBlueBall.getHeight());
		animator.setTarget(mBlueBall);
		animator.setDuration(1000).start();
		// animator.setInterpolator(value)
		animator.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
			}
		});
	}

	/**
	 * 抛物线
	 * 
	 * @param view
	 */
	public void paowuxian(View view){

		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(3000);
		valueAnimator.setObjectValues(new PointF(0, 0));
		valueAnimator.setInterpolator(new BounceInterpolator());
		valueAnimator.setEvaluator(new TypeEvaluator<PointF>(){
			// fraction = t / duration
			//fraction是动画在时间上的执行的比例
			@Override
			public PointF evaluate(float fraction, PointF startValue,
					PointF endValue){
				//fraction*3是时间t
				//假设水平方想的速度是vX
				//垂直方向的位移为s = 0.5*G*t*t
				Log.e("---------", fraction * 3 + "-------------");
				// x方向200px/s ，则y方向0.5 * g * t (g = 100px / s*s)
				PointF point = new PointF();
//				point.x = 200 * fraction * 3;
				point.x = 0;
				point.y = 0.5f * 300 * (fraction * 3) * (fraction * 3);
				return point;
			}
		});

		valueAnimator.start();
		valueAnimator.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				PointF point = (PointF) animation.getAnimatedValue();
				mBlueBall.setX(point.x);
				mBlueBall.setY(point.y);

			}
		});
	}

	public void fadeOut(View view) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0.5f);

		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				Log.e(TAG, "onAnimationEnd");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
				
			}
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationStart(animation);
			}
			
		});

		anim.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				Log.e(TAG, "onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				Log.e(TAG, "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Log.e(TAG, "onAnimationEnd");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				Log.e(TAG, "onAnimationCancel");
			}
		});
		anim.start();
	}
	
	public void onClick(View view) {
	}
	private void resilience(){
		ValueAnimator animator = new ValueAnimator();
		animator.setDuration(5000);
		animator.setObjectValues(new PointF(0,0));
		
	}
	class MyInterpolator extends LinearInterpolator{
		@Override
		public float getInterpolation(float input) {
			return 0;
		}
	}

}
