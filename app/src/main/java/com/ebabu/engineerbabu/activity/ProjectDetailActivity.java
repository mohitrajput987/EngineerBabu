package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.utils.Utils;

public class ProjectDetailActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        context = ProjectDetailActivity.this;
        initView();
        Utils.setUpToolbar(context, "Project Detail");
    }

    private void initView() {

    }

}