package com.paxw.demolist.anim;


import com.paxw.demolist.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Xml4AnimActivity extends Activity
{

	private ImageView mMv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim);

		mMv = (ImageView) findViewById(R.id.id_mv);

	}
	
	/**
	 * 
	 * @Description: 加载资源文件中的xml动画
	 * @author chuang.li
	 * @date 2015-10-28 下午3:01:05
	 * @pramas argType 在res资源文件中新建一个animator的文件夹
	 * 
	 *         <?xml version="1.0" encoding="utf-8"?> 
	 *         <set
	 *         
	 *         xmlns:android="http://schemas.android.com/apk/res/android"
	 *         android:ordering="together" >
	 * 
	 *         <objectAnimator 
	 *     	    android:duration="1000"
	 *    	    android:propertyName="scaleX" 
	 *    		android:valueFrom="1"
	 *         	android:valueTo="0.5" > </objectAnimator> 
	 *         <objectAnimator
	 *        	android:duration="1000"
	 *        	android:propertyName="scaleY"
	 *         	android:valueFrom="1" 
	 *         	android:valueTo="0.5" > </objectAnimator>
	 * 
	 *         </set>
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public void scaleX(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scalex);
		anim.setTarget(mMv);
		anim.start();
	}

	public void scaleXandScaleY(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scale);
		//设置中心
		mMv.setPivotX(0);
		mMv.setPivotY(0);
		//显示的调用invalidate
		mMv.invalidate();
		anim.setTarget(mMv);
		anim.start();
	}

}
