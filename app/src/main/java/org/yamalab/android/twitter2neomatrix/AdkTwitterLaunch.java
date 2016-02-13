package org.yamalab.android.twitter2neomatrix;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class AdkTwitterLaunch extends Activity {
	static final String TAG = "AdkArduinoLaunch";

	static Intent createIntent(Activity activity) {
		Intent intent = new Intent(activity, AdkTwitterActivity.class);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate");

		Intent intent = createIntent(this);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Log.e(TAG, "unable to start DemoKit activity", e);
		}
		finish();
	}

}
