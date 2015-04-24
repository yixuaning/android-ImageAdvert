package com.example.changeimage;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	ImageView full, play;
	ImageView[] imageViews = new ImageView[4];
	int[] tabId = { R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4 };
	int imageIndex = 1, playIndex = 1;
	int imageId[] = { R.drawable.conver1, R.drawable.conver2,
			R.drawable.conver3, R.drawable.conver4 };
	int playImageId[] = { R.drawable.cover_slide1, R.drawable.cover_slide2,
			R.drawable.cover_slide3, R.drawable.cover_slide4,
			R.drawable.cover_slide5, R.drawable.cover_slide6,
			R.drawable.cover_slide7, R.drawable.cover_slide8 };
	Thread imageThread, playThread;
	Animation alphaAnimation;
	boolean isPlay = true;
	RelativeLayout tabLayout;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 要做的事情
			super.handleMessage(msg);
			if (msg.what == 1) {
				play.setBackgroundDrawable(getResources().getDrawable(
						playImageId[playIndex]));
				playIndex++;
				if (playIndex == 8) {
					playIndex = 0;
					full.startAnimation(alphaAnimation);
					full.setBackgroundDrawable(getResources().getDrawable(
							imageId[imageIndex++]));
					if (imageIndex == 4) {
					imageIndex = 0;
				    }
				}
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		full = (ImageView) findViewById(R.id.full);
		play = (ImageView) findViewById(R.id.play);
		tabLayout = (RelativeLayout) findViewById(R.id.tab);
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i] = (ImageView) findViewById(tabId[i]);
			imageViews[i].setOnClickListener(this);
		}
		alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
		playThread = new Thread(new PlayThread());
		playThread.start();
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isPlay = !isPlay;

				if (tabLayout.getVisibility() == View.VISIBLE) {
					tabLayout.setVisibility(View.GONE);
				} else {
					tabLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public class PlayThread implements Runnable {
		// @Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(500);// 线程暂停10秒，单位毫秒
					if (isPlay) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);// 发送消息
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tab1:
			imageIndex=0;
			break;
		case R.id.tab2:
			imageIndex=1;
			break;
		case R.id.tab3:
			imageIndex=2;
			break;
		case R.id.tab4:
			imageIndex=3;
			break;
		}
		full.startAnimation(alphaAnimation);
		full.setBackgroundDrawable(getResources().getDrawable(
				imageId[imageIndex]));
	

	}

}
