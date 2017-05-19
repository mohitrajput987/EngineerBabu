package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.utils.AppPreference;

public class SplashActivity extends AppCompatActivity {

    private Context context;
    private Intent notificationIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        notificationIntent = getIntent();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4 * 1000);
                    startNextActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        thread.start();
    }

    private void startNextActivity() {
        Intent intent = null;
        if (AppPreference.getInstance(context).isLoggedIn()) {
            if (AppPreference.getInstance(context).getUserType().equals(IKeyConstants.USER_TYPE_CLIENT)) {
                intent = new Intent(context, ClientHomeActivity.class);
            } else {
                intent = new Intent(context, CompanyFreelancerHomeActivity.class);
            }
            intent.putExtra(IKeyConstants.NOTIFICATION_TYPE, notificationIntent.getStringExtra(IKeyConstants.NOTIFICATION_TYPE));
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }


}
