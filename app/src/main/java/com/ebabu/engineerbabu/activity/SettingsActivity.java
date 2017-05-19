package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.utils.DialogUtils;
import com.ebabu.engineerbabu.utils.Utils;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = SettingsActivity.this;
        initView();
        Utils.setUpToolbar(context, "Settings");
    }

    private void initView() {
        findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                DialogUtils.openDialogToLogout(context);
                break;
        }
    }
}
