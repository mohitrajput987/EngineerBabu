package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.ConnectAdapter;
import com.ebabu.engineerbabu.beans.Connect;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ConnectsActivity extends AppCompatActivity {

    private final static String TAG = NotificationsActivity.class.getSimpleName();
    private Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView rvConnects;
    private List<Connect> mListConnects;
    private ConnectAdapter connectsAdapter;
    private String createAt = "0";
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connects);
        context = ConnectsActivity.this;
        initView();
        Utils.setUpToolbar(context, "Connects");
    }

    private void initView(){
        mListConnects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mListConnects.add(new Connect());
        }

        rvConnects = (RecyclerView) findViewById(R.id.rv_connects);
        connectsAdapter = new ConnectAdapter(context, mListConnects);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvConnects.setLayoutManager(layoutManager);
        rvConnects.setAdapter(connectsAdapter);

    }
}
