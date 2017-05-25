package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.CachedData;
import com.ebabu.engineerbabu.beans.Platform;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.utils.AppPreference;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.filepicker.Filepicker;

public class SplashActivity extends AppCompatActivity {

    private final static String TAG = SplashActivity.class.getSimpleName();
    private Context context;
    private Intent notificationIntent;
    private final static long CACHE_TIME = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        notificationIntent = getIntent();
        Filepicker.setKey(IKeyConstants.FILESTACK_API_KEY);
        Filepicker.setAppName(getString(R.string.app_name));
        if (Utils.isNetworkConnected(context)) {
            fetchPlatforms();
        } else {
            openDialogOnError(getString(R.string.network_error));
        }
    }

    private void startNextActivity() {
        Intent intent = null;
        if (AppPreference.getInstance(context).isLoggedIn()) {
            if (AppPreference.getInstance(context).getUserType().equals(IKeyConstants.USER_TYPE_CLIENT)) {
                intent = new Intent(context, ClientHomeActivity.class);
            } else {
                if (AppPreference.getInstance(context).getUserRegStatus() == IKeyConstants.REG_STATUS_2) {
                    intent = new Intent(context, SignUpStep2Activity.class);
                } else {
                    intent = new Intent(context, CompanyFreelancerHomeActivity.class);
                }
            }
            intent.putExtra(IKeyConstants.NOTIFICATION_TYPE, notificationIntent.getStringExtra(IKeyConstants.NOTIFICATION_TYPE));
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void fetchPlatforms() {


        new AQuery(context).ajax(IUrlConstants.EXPERTISE_LIST, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "fetchPlatforms(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        Log.d(TAG, "strStatus=" + strStatus);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            JSONArray data = json.getJSONArray("data");
                            Type type = new TypeToken<ArrayList<Platform>>() {
                            }.getType();
                            if (data != null && data.length() > 0) {
                                ArrayList<Platform> tempListNotifications = new Gson().fromJson(data.toString(), type);
                                if (tempListNotifications.size() > 0) {
                                    CachedData.getInstance().setListPlatforms(tempListNotifications);
                                }
                            }
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3 * 1000);
                                        startNextActivity();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            thread.start();
                        } else {
                            if (json.has(IKeyConstants.DATA)) {
                                openDialogOnError(status.getCode() + IKeyConstants.SPACE + json.getString(IKeyConstants.DATA));
                            } else {
                                openDialogOnError(status.getCode() + IKeyConstants.SPACE + getString(R.string.unexpected_error));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        openDialogOnError(getString(R.string.unexpected_error));
                    }
                } else {
                    if (status.getCode() == AjaxStatus.NETWORK_ERROR) {
                        if (Utils.isNetworkConnected(context)) {
                            Toast.makeText(context, getString(R.string.server_is_down), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                    }

                }
            }
        }.header(IKeyConstants.SECRET_KEY, AppPreference.getInstance(context).getSecretKey()).fileCache(false).expire(CACHE_TIME));
    }

    private void openDialogOnError(final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.app_name));

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fetchPlatforms();
            }
        });

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
