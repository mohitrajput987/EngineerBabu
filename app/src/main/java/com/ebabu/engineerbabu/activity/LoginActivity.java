package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.ebabu.engineerbabu.customview.MyLoading;
import com.ebabu.engineerbabu.utils.AppPreference;
import com.ebabu.engineerbabu.utils.DialogUtils;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = LoginActivity.class.getSimpleName();
    private Context context;
    private CustomEditText etEmail, etPassword;
    private String email, password, fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        initView();
    }

    private void initView() {
        etEmail = (CustomEditText) findViewById(R.id.et_email);
        etPassword = (CustomEditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_post_project).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forgot_password).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_post_project:
                intent = new Intent(context, PostProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_register:
                intent = new Intent(context, SelectUserTypeActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
//                intent = new Intent(context, CompanyFreelancerHomeActivity.class);
//                startActivity(intent);
                if (areFieldsValid()) {
                    new FcmTokenFetchingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;

            case R.id.btn_forgot_password:
                openForgetPasswordDialog();
                break;
        }
    }


    private class FcmTokenFetchingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (fcmToken == null || fcmToken.isEmpty()) {
                    fcmToken = FirebaseInstanceId.getInstance().getToken();
                }
                Log.d(TAG, "deviceToken=" + fcmToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (!fcmToken.isEmpty()) {
                    login();
                } else {
                    new FcmTokenFetchingTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            } catch (Exception e) {
                DialogUtils.openAlertToShowMessage(getResources().getString(R.string.unexpected_error), context);
            }
            super.onPostExecute(aVoid);
        }

    }

    public boolean areFieldsValid() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();


        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
            return false;
        }

        if (!Utils.isValidEmail(email)) {
            etEmail.setError(getString(R.string.enter_a_valid_email));
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
            return false;
        }

        if (password.length() < IKeyConstants.MIN_PASSWORD_LENGTH) {
            etPassword.setError(getString(R.string.password_length_at_least_n_digits, IKeyConstants.MIN_PASSWORD_LENGTH));
            return false;
        }

        return true;
    }

    private void login() {


        final MyLoading myLoading = new MyLoading(context);
        myLoading.show(getString(R.string.please_wait));

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("device_token", fcmToken);
            jsonObject.put("device_id", IKeyConstants.EMPTY);
            jsonObject.put("device_type", "Android");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "login(): jsonObject=" + jsonObject);

        new AQuery(context).post(IUrlConstants.LOGIN, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "login(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            json = json.getJSONObject("data");
                            String userType = IKeyConstants.EMPTY;

                            if (json.has(AppPreference.USER_TYPE))
                                userType = json.getString(AppPreference.USER_TYPE);

                            if (json.has(AppPreference.USER_ID)) {
                                AppPreference.getInstance(context).setUserId(json.getString(AppPreference.USER_ID));
                            }

                            if (json.has(AppPreference.FIRST_NAME))
                                AppPreference.getInstance(context).setFirstName(json.getString(AppPreference.FIRST_NAME));

                            if (json.has(AppPreference.LAST_NAME))
                                AppPreference.getInstance(context).setLastName(json.getString(AppPreference.LAST_NAME));

                            if (json.has(AppPreference.EMAIL))
                                AppPreference.getInstance(context).setEmail(json.getString(AppPreference.EMAIL));

                            if (json.has(AppPreference.MOBILE_NUM))
                                AppPreference.getInstance(context).setMobileNum(json.getString(AppPreference.MOBILE_NUM));

                            AppPreference.getInstance(context).setPassword(password);
                            AppPreference.getInstance(context).setUserType(userType);

                            if (json.has(AppPreference.COUNTRY))
                                AppPreference.getInstance(context).setCountry(json.getString(AppPreference.COUNTRY));

                            if (json.has(AppPreference.CITY))
                                AppPreference.getInstance(context).setCity(json.getString(AppPreference.CITY));

                            if (json.has(AppPreference.PROFILE_IMAGE))
                                AppPreference.getInstance(context).setProfileImage(json.getString(AppPreference.PROFILE_IMAGE));

                            AppPreference.getInstance(context).setFcmToken(fcmToken);

                            if (json.has(AppPreference.COMPANY_NAME))
                                AppPreference.getInstance(context).setCompanyName(json.getString(AppPreference.COMPANY_NAME));

                            if (json.has(AppPreference.FB_LINK)) {
                                AppPreference.getInstance(context).setFbLink(json.getString(AppPreference.FB_LINK));
                            }
                            if (json.has(AppPreference.TWITTER_LINK)) {
                                AppPreference.getInstance(context).setTwitterLink(json.getString(AppPreference.TWITTER_LINK));
                            }
                            if (json.has(AppPreference.LINKEDIN_LINK)) {
                                AppPreference.getInstance(context).setLinkedinLink(json.getString(AppPreference.LINKEDIN_LINK));
                            }

                            if (json.has(AppPreference.USER_TEAM_SIZE) && !json.getString(AppPreference.USER_TEAM_SIZE).isEmpty())
                                AppPreference.getInstance(context).setUserTeamSize(json.getInt(AppPreference.USER_TEAM_SIZE));

                            if (json.has(AppPreference.USER_MIN_PROJECT_AMOUNT) && !json.getString(AppPreference.USER_MIN_PROJECT_AMOUNT).isEmpty())
                                AppPreference.getInstance(context).setMinProjectAmnt(json.getInt(AppPreference.USER_MIN_PROJECT_AMOUNT));

                            if (json.has(AppPreference.USER_MAX_PROJECT_AMOUNT) && !json.getString(AppPreference.USER_MAX_PROJECT_AMOUNT).isEmpty())
                                AppPreference.getInstance(context).setMaxProjectAmnt(json.getInt(AppPreference.USER_MAX_PROJECT_AMOUNT));

                            if (json.has(AppPreference.USER_URL)) {
                                AppPreference.getInstance(context).setUserUrl(json.getString(AppPreference.USER_URL));
                            }

                            if (json.has(AppPreference.USER_REG_DATE)) {
                                AppPreference.getInstance(context).setUserRegDate(json.getString(AppPreference.USER_REG_DATE));
                            }

                            if (json.has(AppPreference.USER_URL)) {
                                AppPreference.getInstance(context).setUserUrl(json.getString(AppPreference.USER_URL));
                            }

                            if (json.has(AppPreference.USER_PROFILE_COMPLETED) && !json.getString(AppPreference.USER_PROFILE_COMPLETED).isEmpty())
                                AppPreference.getInstance(context).setUserProfileCompletion(json.getInt(AppPreference.USER_PROFILE_COMPLETED));

                            if (json.has(AppPreference.USER_STATUS))
                                AppPreference.getInstance(context).setUserStatus(json.getString(AppPreference.USER_STATUS));

                            if (json.has(AppPreference.USER_EMAIL_CONFIRMED) && !json.getString(AppPreference.USER_EMAIL_CONFIRMED).isEmpty())
                                AppPreference.getInstance(context).setUserEmailConfirmed(json.getInt(AppPreference.USER_EMAIL_CONFIRMED));

                            if (json.has(AppPreference.USER_SUBSCRIPTION_TYPE) && !json.getString(AppPreference.USER_SUBSCRIPTION_TYPE).isEmpty())
                                AppPreference.getInstance(context).setUserSubscriptionType(json.getInt(AppPreference.USER_SUBSCRIPTION_TYPE));

                            if (json.has(AppPreference.USER_SUBSCRIPTION_VALID_TILL) && !json.getString(AppPreference.USER_SUBSCRIPTION_VALID_TILL).isEmpty())
                                AppPreference.getInstance(context).setUserSubscriptionValidTill(json.getString(AppPreference.USER_SUBSCRIPTION_VALID_TILL));

                            if (json.has(AppPreference.USER_SUBSCRIPTION_STATUS) && !json.getString(AppPreference.USER_SUBSCRIPTION_STATUS).isEmpty())
                                AppPreference.getInstance(context).setUserSubscriptionStatus(json.getInt(AppPreference.USER_SUBSCRIPTION_STATUS));

                            if (json.has(AppPreference.USER_SUBSCRIPTION_STATUS) && !json.getString(AppPreference.USER_SUBSCRIPTION_STATUS).isEmpty())
                                AppPreference.getInstance(context).setUserSubscriptionStatus(json.getInt(AppPreference.USER_SUBSCRIPTION_STATUS));

                            if (json.has(AppPreference.STAT_WEBSITE_VISITS) && !json.getString(AppPreference.STAT_WEBSITE_VISITS).isEmpty())
                                AppPreference.getInstance(context).setStatWebsiteVisits(json.getInt(AppPreference.STAT_WEBSITE_VISITS));

                            if (json.has(AppPreference.STAT_PROFILE_VIEWS) && !json.getString(AppPreference.STAT_PROFILE_VIEWS).isEmpty())
                                AppPreference.getInstance(context).setStatProfileViews(json.getInt(AppPreference.STAT_PROFILE_VIEWS));

                            if (json.has(AppPreference.SECRET_KEY) && !json.getString(AppPreference.SECRET_KEY).isEmpty())
                                AppPreference.getInstance(context).setSecretKey(json.getString(AppPreference.SECRET_KEY));

                            if (json.has(AppPreference.USER_REG_STATUS) && !json.getString(AppPreference.USER_REG_STATUS).isEmpty())
                                AppPreference.getInstance(context).setUserRegStatus(json.getInt(AppPreference.USER_REG_STATUS));

                            if (json.has(AppPreference.USER_ABOUT) && !json.getString(AppPreference.USER_ABOUT).isEmpty())
                                AppPreference.getInstance(context).setUserAbout(json.getString(AppPreference.USER_ABOUT));


                            Intent intent = null;
                            if (userType.equals(IKeyConstants.USER_TYPE_CLIENT)) {
                                intent = new Intent(context, ClientHomeActivity.class);
                            }else{
                                intent = new Intent(context, CompanyFreelancerHomeActivity.class);
                            }

                            //Core.registerDeviceToken(context, fcmToken);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, json.getString(IKeyConstants.DATA), Toast.LENGTH_LONG).show();
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
                myLoading.dismiss();
            }
        });
    }

    private void openForgetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Forgot Password");

        final View view = getLayoutInflater().inflate(R.layout.layout_dialog_forget_password, null);
        builder.setView(view);

        final CustomEditText etEmail = (CustomEditText) view.findViewById(R.id.et_email);
        etEmail.setText(this.etEmail.getText().toString());

        builder.setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();

                if (email.isEmpty()) {
                    etEmail.setError(getString(R.string.enter_email));
                    return;
                }

                if (!Utils.isValidEmail(email)) {
                    etEmail.setError(getString(R.string.enter_a_valid_email));
                    return;
                }

                sendEmailOnForgotPassword(email, alertDialog);
            }
        });
    }

    private void sendEmailOnForgotPassword(String email, final AlertDialog alertDialog) {
        final MyLoading myLoading = new MyLoading(context);
        myLoading.show("Logging in");

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "sendEmailOnForgotPassword(): jsonObject=" + jsonObject);

        new AQuery(context).post(IUrlConstants.FORGOT_PASSWORD, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "sendEmailOnForgotPassword(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            alertDialog.dismiss();
                            Toast.makeText(context, "We have sent you a password reset link on your mail. Please check and reset your password.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, json.getString(IKeyConstants.DATA), Toast.LENGTH_LONG).show();
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
                myLoading.dismiss();
            }
        });
    }
}
