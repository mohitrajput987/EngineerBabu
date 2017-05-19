package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.SubscriptionAdapter;
import com.ebabu.engineerbabu.beans.Subscription;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionActivity extends AppCompatActivity {

    private final static String TAG = NotificationsActivity.class.getSimpleName();
    private Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView rvNotifications;
    private List<Subscription> mListSubscription;
    private SubscriptionAdapter subscriptionAdapter;
    private String createAt = "0";
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        context = SubscriptionActivity.this;
        initView();
        Utils.setUpToolbar(context, "Subscription");
    }

    private void initView(){
        mListSubscription = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mListSubscription.add(new Subscription());
        }

        rvNotifications = (RecyclerView) findViewById(R.id.rv_subscription);
        subscriptionAdapter = new SubscriptionAdapter(context, mListSubscription);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvNotifications.setLayoutManager(layoutManager);
        rvNotifications.setAdapter(subscriptionAdapter);

    }
}
