package com.paxw.demolist.progressbar;


import com.paxw.demolist.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class ProgressBarPai extends Activity implements OnClickListener {

	private BingZhuangPro progressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_bar_pai);
		progressView = ((BingZhuangPro)findViewById(R.id.pie));
		progressView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Thread t = new Thread(){
			@Override
			public void run() {
				while (true) {
					try {
						this.sleep(500l);
						handler.sendEmptyMessage(11);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		
	}
	private int progress =0;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (progress>360) {
				progress =0;
			}
			
			progressView.setProgress(progress);
			progress+=1;
			
			
		};
	};
	
}
