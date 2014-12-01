package cn.joim.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import cn.joim.controller.R.id;

public class MainActivity extends ActionBarActivity {

	// views
	private View layoutContents;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViewAndUpdateTxt(null);
	}

	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		setIntent(intent);
		initViewAndUpdateTxt(getIntent().getStringExtra("content"));
	}

	private void initViewAndUpdateTxt(String txt) {

		if (null == layoutContents) {
			layoutContents = findViewById(id.layout_content);
			mTextView = (TextView) findViewById(id.txt_content);

			// get view's heght/width in onCreate().
			layoutContents.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							/**
							 * 如果我们需要在这里因为某些需求需要设置content的高度；
							 * 那么如果后续需要通过onNewIntent（）去
							 * 更新内容会导致content布局的高度不能动态更新了，不过似乎在
							 * 更新之前我强制将布局的参数设置为WRAP_CONTENT
							 * 就可以解决这个问题，不知道效果怎么样，明天去单位 在项目上再试一下。
							 * */
							Log.i("joim", "动态设置content的高度.");
							int heightContent = layoutContents
									.getMeasuredHeight();
							layoutContents.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);

							RelativeLayout.LayoutParams mLayoutParams = (LayoutParams) layoutContents
									.getLayoutParams();
							mLayoutParams.height = heightContent;
							layoutContents.setLayoutParams(mLayoutParams);

						}
					});
		}

		if (!TextUtils.isEmpty(txt)) {
			RelativeLayout.LayoutParams mLayoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutContents.setLayoutParams(mLayoutParams);
			mTextView.setText(txt);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		mHandler.sendEmptyMessageDelayed(0, 5000);
	}

	private Handler mHandler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			super.dispatchMessage(msg);
			Intent mIntent = new Intent("android.update.content");
			sendBroadcast(mIntent);
			mHandler.sendEmptyMessageDelayed(0, 10000);

		};
	};

	protected void onPause() {
		mHandler.removeMessages(0);
		super.onPause();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
