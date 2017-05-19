package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.DataHandler;
import com.ebabu.engineerbabu.constant.IKeyConstants;

public class SelectUserTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);
        context = SelectUserTypeActivity.this;
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_compnay).setOnClickListener(this);
        findViewById(R.id.btn_freelancer).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, SignUpStep1Activity.class);
        switch (view.getId()) {
            case R.id.btn_compnay:
                DataHandler.getInstance().setUserType(IKeyConstants.USER_TYPE_COMPANY);
                break;

            case R.id.btn_freelancer:
                DataHandler.getInstance().setUserType(IKeyConstants.USER_TYPE_FREELANCER);
                break;
        }
        startActivity(intent);
    }
}
