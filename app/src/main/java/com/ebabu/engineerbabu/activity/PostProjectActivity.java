package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.PlatformAdapter;
import com.ebabu.engineerbabu.beans.CachedData;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.customview.CustomEditText;
import com.ebabu.engineerbabu.utils.Utils;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class PostProjectActivity extends AppCompatActivity {

    private final static String TAG = PostProjectActivity.class.getSimpleName();
    private Context context;
    private RecyclerView rvPlatform;
    private PlatformAdapter platformAdapter;
    private CountryCodePicker countryCodePicker;
    private String csvPlatforms, title, description, fullname, email, mobile;
    private CustomEditText etTitle, etDescription, etName, etEmail, etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        context = PostProjectActivity.this;
        initView();
    }

    private void initView() {
        countryCodePicker = (CountryCodePicker) findViewById(R.id.ccp);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf");
        countryCodePicker.setTypeFace(typeFace);
        rvPlatform = (RecyclerView) findViewById(R.id.rv_platform);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        platformAdapter = new PlatformAdapter(context, CachedData.getInstance().getListPlatforms());
        rvPlatform.setLayoutManager(layoutManager);
        rvPlatform.setAdapter(platformAdapter);

        findViewById(R.id.btn_post_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProject();
            }
        });
    }

    private boolean areFieldsValid() {
        title = etTitle.getText().toString();
        description = etDescription.getText().toString();
        fullname = etName.getText().toString();
        email = etEmail.getText().toString();
        mobile = etMobile.getText().toString();
        csvPlatforms = platformAdapter.getSelectedPlatformsInCsv();

        if (csvPlatforms.isEmpty()) {
            Toast.makeText(context, "Select at least one platform", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (title.isEmpty()) {
            etTitle.setError("Enter project title");
            return false;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter project description");
            return false;
        }

        if (fullname.isEmpty()) {
            etName.setError("Enter name");
            return false;
        }

        if (!Utils.isNameValid(fullname)) {
            etName.setError("Enter a valid name");
            return false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter email");
            return false;
        }

        if (!Utils.isValidEmail(email)) {
            etEmail.setError("Enter a valid email");
            return false;
        }

        if (mobile.isEmpty()) {
            etMobile.setError("Enter mobile number");
            return false;
        }

        if (mobile.length() < IKeyConstants.MIN_MOBILE_LENGTH) {
            etMobile.setError("Mobile number is too short");
            return false;
        }

        return true;
    }


    private void postProject() {

        if (!Utils.isNetworkConnected(context)) {
            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!areFieldsValid()) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("", csvPlatforms);
            jsonObject.put("", title);
            jsonObject.put("", description);
            jsonObject.put("", fullname);
            jsonObject.put("", email);
            jsonObject.put("", countryCodePicker.getSelectedCountryCode());
            jsonObject.put("", countryCodePicker.getSelectedCountryNameCode());
            jsonObject.put("", mobile);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "postProject(): jsonObject=" + jsonObject);
        new AQuery(context).post(IUrlConstants.POST_PROJECT, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "postProject(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        Log.d(TAG, "strStatus=" + strStatus);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            Toast.makeText(context, "Project posted successfully. Please check your email.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {

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
            }
        });
    }

}
