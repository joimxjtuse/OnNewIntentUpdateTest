package cn.joim.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyContentUpdateReceiver extends BroadcastReceiver {

	private static String content = "";

	@Override
	public void onReceive(Context context, Intent mIntent) {

		String res = "I'm joim, this is my test for onNewIntent(). ";
		content += res;

		Intent newIntent = new Intent(context, MainActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		newIntent.putExtra("content", content);
		context.startActivity(newIntent);
	}
}
