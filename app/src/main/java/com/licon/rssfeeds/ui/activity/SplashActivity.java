package com.licon.rssfeeds.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, AppData.SPLASH_TIME_OUT);
    }
}