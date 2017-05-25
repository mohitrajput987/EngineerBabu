package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.customview.CustomEditText;
import com.ebabu.engineerbabu.utils.AppPreference;
import com.ebabu.engineerbabu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpStep2Activity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = SignUpStep2Activity.class.getSimpleName();
    private Context context;
    private CustomEditText etAboutMe, etUrl, etFbUrl, etTwitterUrl, etLinkedinUrl;
    private String aboutMe, url, fbUrl, twitterUrl, linkedinUrl;
    private final static int MIN_ABOUT_ME_LENGTH = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left,
                R.anim.left_to_right);
        setContentView(R.layout.activity_sign_up_step2);
        context = SignUpStep2Activity.this;
        initView();
        Utils.setUpToolbar(context, IKeyConstants.EMPTY);
    }

    private void initView() {
        etAboutMe = (CustomEditText) findViewById(R.id.et_about_me);
        etUrl = (CustomEditText) findViewById(R.id.et_company_url);
        etFbUrl = (CustomEditText) findViewById(R.id.et_facebook);
        etTwitterUrl = (CustomEditText) findViewById(R.id.et_twitter);
        etLinkedinUrl = (CustomEditText) findViewById(R.id.et_linked_in);
        findViewById(R.id.btn_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                Intent intent = new Intent(context, SignUpStep3Activity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);
        super.finish();
    }

    private boolean areFieldsValid() {
        etAboutMe.setError(null);
        etUrl.setError(null);
        etFbUrl.setError(null);
        etTwitterUrl.setError(null);
        etLinkedinUrl.setError(null);

        aboutMe = etAboutMe.getText().toString();
        url = etUrl.getText().toString();
        fbUrl = etFbUrl.getText().toString();
        twitterUrl = etTwitterUrl.getText().toString();
        linkedinUrl = etLinkedinUrl.getText().toString();

        if (aboutMe.isEmpty()) {
            etAboutMe.setError("Write something about you");
            return false;
        }

        if (aboutMe.length() < MIN_ABOUT_ME_LENGTH) {
            etAboutMe.setError("Write in at least " + MIN_ABOUT_ME_LENGTH + " characters");
            return false;
        }

        if (!url.isEmpty()) {
            if (!Utils.isWebsiteValid(url)) {
                etUrl.setError("Enter a valid url or leave it blank");
                return false;
            }
        }

        if (!linkedinUrl.isEmpty()) {
            if (!Utils.isWebsiteValid(linkedinUrl)) {
                etLinkedinUrl.setError("Enter a valid url or leave it blank");
                return false;
            }
        }

        if (!fbUrl.isEmpty()) {
            if (!Utils.isWebsiteValid(fbUrl)) {
                etFbUrl.setError("Enter a valid url or leave it blank");
                return false;
            }
        }

        if (!twitterUrl.isEmpty()) {
            if (!Utils.isWebsiteValid(twitterUrl)) {
                etTwitterUrl.setError("Enter a valid url or leave it blank");
                return false;
            }
        }

        return true;
    }

    private void registerUser() {

        if (!Utils.isNetworkConnected(context)) {
            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!areFieldsValid()) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("about_you", aboutMe);
            jsonObject.put("user_url", url);
            jsonObject.put("facebook", fbUrl);
            jsonObject.put("twitter", twitterUrl);
            jsonObject.put("linkedin", linkedinUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "registerUser(): jsonObject=" + jsonObject);
        new AQuery(context).post(IUrlConstants.REGISTER_PART_2, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "registerUser(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            Toast.makeText(context, "You have updated your information successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, SignUpStep3Activity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, json.getString(IKeyConstants.DATA), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
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
        }.header(IKeyConstants.SECRET_KEY, AppPreference.getInstance(context).getSecretKey()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
