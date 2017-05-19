package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.NotificationAdapter;
import com.ebabu.engineerbabu.beans.Notification;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.customview.MyLoading;
import com.ebabu.engineerbabu.utils.AppPreference;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private final static String TAG = NotificationsActivity.class.getSimpleName();
    private Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView rvNotifications;
    private List<Notification> mListNotifications;
    private NotificationAdapter notificationAdapter;
    private String createAt = "0";
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        context = NotificationsActivity.this;
        initView();
        Utils.setUpToolbar(context, "Notifications");
        if (Utils.isNetworkConnected(context)) {
            fetchNotificationsList();
        } else {
            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rvNotifications = (RecyclerView) findViewById(R.id.rv_notifications);
        mListNotifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(context, mListNotifications);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvNotifications.setLayoutManager(layoutManager);
        rvNotifications.setAdapter(notificationAdapter);

        rvNotifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.d(TAG, "visibleItemCount: " + visibleItemCount + ", totalItemCount: " + totalItemCount + ", pastVisiblesItems: " + pastVisiblesItems);
                            fetchNotificationsList();
                        }
                    }
                }
            }


        });
    }

    private void fetchNotificationsList() {

        final MyLoading myLoading = new MyLoading(context);
        if (mListNotifications.size() == 0) {
            myLoading.show("Loading notifications");
        }

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("create_at", createAt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loading = true;
        Log.d(TAG, "fetchNotificationsList(): jsonObject=" + jsonObject);
        new AQuery(context).post(IUrlConstants.NOTIFICATIONS_LIST, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "fetchNotificationsList(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            JSONArray data = json.getJSONArray("data");
                            Type type = new TypeToken<ArrayList<Notification>>() {
                            }.getType();

                            if (data != null && data.length() > 0) {
                                ArrayList<Notification> tempListNotifications = new Gson().fromJson(data.toString(), type);
                                if (tempListNotifications.size() > 0) {
                                    int previousSize = mListNotifications.size();
                                    mListNotifications.addAll(previousSize, tempListNotifications);
                                    notificationAdapter.notifyItemRangeInserted(previousSize, tempListNotifications.size());
                                    createAt = tempListNotifications.get(tempListNotifications.size() - 1).getCreate_at();
                                }
                            }
                        } else {
                            if (mListNotifications.size() == 0) {
                                Toast.makeText(context, json.getString(IKeyConstants.DATA), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (status.getCode() == AjaxStatus.NETWORK_ERROR)
                        Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                }
                loading = false;
                myLoading.dismiss();
            }
        }.header(IKeyConstants.SECRET_KEY, AppPreference.getInstance(context).getSecretKey()));
    }

    
}
