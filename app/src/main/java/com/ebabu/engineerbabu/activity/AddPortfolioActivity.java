package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.PlatformAdapter;
import com.ebabu.engineerbabu.beans.CachedData;
import com.ebabu.engineerbabu.utils.Utils;

public class AddPortfolioActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvPlatform;
    private PlatformAdapter platformAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portfolio);
        context = AddPortfolioActivity.this;
        initView();
        Utils.setUpToolbar(context, "Add Portfolio");
    }

    private void initView() {
        rvPlatform = (RecyclerView) findViewById(R.id.rv_platform);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        platformAdapter = new PlatformAdapter(context, CachedData.getInstance().getListPlatforms());
        rvPlatform.setLayoutManager(layoutManager);
        rvPlatform.setAdapter(platformAdapter);
    }
}
