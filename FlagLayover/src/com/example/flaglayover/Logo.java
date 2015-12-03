package com.example.flaglayover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public  class Logo extends Activity {
	Handler mHandler;
	Runnable mNextActivityCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.logo);
	    mHandler = new Handler();
	    mNextActivityCallback = new Runnable() {
	        @Override
	        public void run() {
	            // Intent to jump to the next activity
	            Intent intent= new Intent(Logo.this, MainActivity.class);
	            startActivity(intent);
	            finish(); // so the splash activity goes away
	        }
	    };
	    mHandler.postDelayed(mNextActivityCallback, 1500L);
	}

}
