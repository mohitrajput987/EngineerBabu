package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.PlatformAdapter;
import com.ebabu.engineerbabu.beans.Skill;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.List;

public class SignUpStep3Activity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = SignUpStep3Activity.class.getSimpleName();
    private Context context;
    private RecyclerView rvPlatform;
    private PlatformAdapter platformAdapter;
    private CustomTextView btnAddSkills;
    private List<Skill> listSkills;
    private final static int RQ_ADD_SKILLS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left,
                R.anim.left_to_right);
        setContentView(R.layout.activity_sign_up_step3);
        context = SignUpStep3Activity.this;
        initView();
        Utils.setUpToolbar(context, IKeyConstants.EMPTY);
    }

    private void initView() {
        rvPlatform = (RecyclerView) findViewById(R.id.rv_platform);
        btnAddSkills = (CustomTextView) findViewById(R.id.btn_add_skills);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        platformAdapter = new PlatformAdapter(context, Utils.getPlatformList());
        rvPlatform.setLayoutManager(layoutManager);
        rvPlatform.setAdapter(platformAdapter);

        btnAddSkills.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_skills:
                Intent intent = new Intent(context, SkillsSelectionActivity.class);
                startActivityForResult(intent, RQ_ADD_SKILLS);
                break;
        }
    }
}
