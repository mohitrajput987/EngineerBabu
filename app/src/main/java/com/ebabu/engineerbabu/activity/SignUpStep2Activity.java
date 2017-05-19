package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.utils.Utils;

public class SignUpStep2Activity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left,
                R.anim.left_to_right);
        setContentView(R.layout.activity_sign_up_step2);
        context = SignUpStep2Activity.this;
        initView();
        Utils.setUpToolbar(context, IKeyConstants.EMPTY);
    }

    private void initView() {
        findViewById(R.id.btn_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                Intent intent = new Intent(context, SignUpStep3Activity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
