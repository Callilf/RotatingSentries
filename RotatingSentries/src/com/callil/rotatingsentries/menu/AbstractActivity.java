package com.callil.rotatingsentries.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.callil.rotatingsentries.GameActivity;

public abstract class AbstractActivity extends Activity {
	
	private static final String TAG = AbstractActivity.class.getSimpleName();
	
	protected static final String RESULT = "result";
	protected static final String RETURN_NOTHING = "nothing";
	
	private String tagParent;

	/**
	 * Kill the application
	 */
	protected void exitApplication() {
		finish();
		System.gc();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set fullscreen and remove the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().getDecorView().setBackgroundColor(Color.BLACK);
	}
	
	@Override
	protected void onDestroy() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - Destroying...");
		}
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - Stopping...");
		}
		super.onStop();
	}

	@Override
	protected void onPause() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - Pausing...");
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - Resuming...");
		}
		super.onResume();
	}

	@Override
	protected void onRestart() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - Restarting...");
		}
		super.onRestart();
	}

	@Override
	protected void onUserLeaveHint() {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - User leaving hint...");
		}
		super.onUserLeaveHint();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (GameActivity.DEBUG_MODE) {
			Log.d(TAG, tagParent + " - SavingInstanceState...");
		}
		super.onSaveInstanceState(outState);
	}
	
	public void setTagParent(String tagParent) {
		this.tagParent = tagParent;
	}

	/**
	 * Return a result and finish the activity
	 * 
	 * @param result the result
	 */
	public void returnResult(String result) {
		 Intent returnIntent = new Intent();
		 if(result != null) {
			 returnIntent.putExtra(RESULT, result);
			 setResult(RESULT_OK,returnIntent);
		 } else {
			 setResult(RESULT_CANCELED,returnIntent);
		 }
		 finish();
	}

}
