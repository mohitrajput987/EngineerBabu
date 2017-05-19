package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.PlatformAdapter;
import com.ebabu.engineerbabu.utils.Utils;
import com.hbb20.CountryCodePicker;

public class PostProjectActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvPlatform;
    private PlatformAdapter platformAdapter;
    private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        context = PostProjectActivity.this;
        initView();
    }

    private void initView() {
        countryCodePicker = (CountryCodePicker) findViewById(R.id.ccp);
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"Bariol_Regular.otf");
        countryCodePicker.setTypeFace(typeFace);
        rvPlatform = (RecyclerView) findViewById(R.id.rv_platform);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        platformAdapter = new PlatformAdapter(context, Utils.getPlatformList());
        rvPlatform.setLayoutManager(layoutManager);
        rvPlatform.setAdapter(platformAdapter);
    }

}
