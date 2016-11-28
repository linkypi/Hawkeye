package com.lynch.hawkeye.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lynch.hawkeye.R;
import com.lynch.hawkeye.TestActivity;
import com.lynch.hawkeye.utils.Utils;

public class SplashActivity extends Activity {

    private boolean _firstOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UMShareAPI.get(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        _firstOpen = Utils.isFirstOpen(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (_firstOpen) {
                    Intent intent = new Intent(SplashActivity.this, TestActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
