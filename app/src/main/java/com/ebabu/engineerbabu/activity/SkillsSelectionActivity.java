package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.SkillsAdapter;
import com.ebabu.engineerbabu.beans.Skill;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.customview.CustomEditText;
import com.ebabu.engineerbabu.customview.MyLoading;
import com.ebabu.engineerbabu.utils.AppPreference;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SkillsSelectionActivity extends AppCompatActivity {

    private final static String TAG = SkillsSelectionActivity.class.getSimpleName();
    private Context context;
    private RecyclerView rvPlatform;
    private SkillsAdapter skillAdapter;
    private List<Skill> listSkills;
    private CustomEditText etSearchSkills;
    private final static long CACHE_TIME = 15 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_selection);
        context = SkillsSelectionActivity.this;
        initView();
        Utils.setUpToolbar(context, "Add Skills");
        if (Utils.isNetworkConnected(context)) {
            fetchSkillsList();
        } else {
            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        listSkills = new ArrayList<>();
//        for (int i = 0; i < Utils.PLATFORM_NAMES.length; i++) {
//            listSkills.add(new Skill(i + 1, Utils.PLATFORM_NAMES[i]));
//        }

        rvPlatform = (RecyclerView) findViewById(R.id.rv_platform);
        etSearchSkills = (CustomEditText) findViewById(R.id.et_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        skillAdapter = new SkillsAdapter(context, listSkills);
        rvPlatform.setLayoutManager(layoutManager);
        rvPlatform.setAdapter(skillAdapter);

        etSearchSkills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                skillAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void fetchSkillsList() {

        final MyLoading myLoading = new MyLoading(context);
        myLoading.show("Loading skills");

        new AQuery(context).ajax(IUrlConstants.SKILLS_LIST + "?search_key=", JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "fetchSkillsList(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        Log.d(TAG, "strStatus=" + strStatus);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            JSONArray data = json.getJSONArray("data");
                            Type type = new TypeToken<ArrayList<Skill>>() {
                            }.getType();
                            if (data != null && data.length() > 0) {
                                ArrayList<Skill> tempListNotifications = new Gson().fromJson(data.toString(), type);
                                if (tempListNotifications.size() > 0) {
                                    listSkills.clear();
                                    listSkills.addAll(tempListNotifications);
                                    skillAdapter.notifyDataSetChanged();
                                    etSearchSkills.setText("A");
                                    etSearchSkills.setText(IKeyConstants.EMPTY);
                                }
                            }
                        } else {
                            if (listSkills.size() == 0) {
                                Toast.makeText(context, json.getString(IKeyConstants.DATA), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    if (status.getCode() == AjaxStatus.NETWORK_ERROR)
                        Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();

                    finish();
                }
                myLoading.dismiss();
            }
        }.header(IKeyConstants.SECRET_KEY, AppPreference.getInstance(context).getSecretKey()).fileCache(false).expire(CACHE_TIME));
    }
}
