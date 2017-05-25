package com.ebabu.engineerbabu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.DataHandler;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.constant.IUrlConstants;
import com.ebabu.engineerbabu.customview.CustomEditText;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.ebabu.engineerbabu.utils.ImageUtils;
import com.ebabu.engineerbabu.utils.PermissionsUtils;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.filepicker.Filepicker;
import io.filepicker.models.FPFile;

public class SignUpStep1Activity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = SignUpStep1Activity.class.getSimpleName();
    private Context context;
    private CountryCodePicker countryCodePicker;
    private CustomTextView tvCity;
    private CircleImageView ivProfilePic;
    private CustomEditText etCompanyName, etFirstName, etLastName, etEmail, etPassword, etMobile;
    private String address, imageUrl = IKeyConstants.EMPTY, companyName, firstName, lastName, email, password, mobile;
    private double latitude, longitude;
    private ImageUtils imageUtils;
    private final static int MIN_PASSWORD_LENGTH = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step1);
        context = SignUpStep1Activity.this;
        initView();
        Utils.setUpToolbar(context, IKeyConstants.EMPTY);
    }

    private void initView() {
        ivProfilePic = (CircleImageView) findViewById(R.id.iv_profile_pic);
        tvCity = (CustomTextView) findViewById(R.id.tv_city);
        etCompanyName = (CustomEditText) findViewById(R.id.et_company_name);
        etFirstName = (CustomEditText) findViewById(R.id.et_fname);
        etLastName = (CustomEditText) findViewById(R.id.et_lname);
        etEmail = (CustomEditText) findViewById(R.id.et_email);
        etPassword = (CustomEditText) findViewById(R.id.et_password);
        etMobile = (CustomEditText) findViewById(R.id.et_mobile);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.ccp);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "Bariol_Regular.otf");
        countryCodePicker.setTypeFace(typeFace);
        findViewById(R.id.btn_next).setOnClickListener(this);
        tvCity.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);

        if (DataHandler.getInstance().getUserType() == IKeyConstants.USER_TYPE_FREELANCER) {
            etCompanyName.setVisibility(View.GONE);
        }

        imageUtils = new ImageUtils(context, ivProfilePic);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                registerUser();
                break;

            case R.id.tv_city:
                Utils.startAddressActivity(context, Utils.PLACE_AUTOCOMPLETE_REQUEST_CODE_PICKUP);
                break;

            case R.id.iv_profile_pic:
                onProfilePictureClicked();
                break;
        }
    }

    private void onProfilePictureClicked() {
        if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
            if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write External Storage")) {
                return;
            }
        }
        Intent intent = new Intent(this, Filepicker.class);
        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Utils.PLACE_AUTOCOMPLETE_REQUEST_CODE_PICKUP) {
                Place place = PlaceAutocomplete.getPlace(context, data);
                LatLng latLng = place.getLatLng();
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                address = place.getAddress().toString();
                tvCity.setText(address);
            } else if (requestCode == Filepicker.REQUEST_CODE_GETFILE) {
                // Filepicker always returns array of FPFile objects
                ArrayList<FPFile> fpFiles = data.getParcelableArrayListExtra(Filepicker.FPFILES_EXTRA);
                // Option multiple was not set so only 1 object is expected
                FPFile file = fpFiles.get(0);
                imageUrl = file.getUrl();
                new AQuery(context).id(ivProfilePic).image(imageUrl);
                Log.d(TAG, "onActivityResult(): imageUrl=" + imageUrl);
            } else {
                imageUtils.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private boolean areFieldsValid() {
        companyName = etCompanyName.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        mobile = etMobile.getText().toString();

        etCompanyName.setError(null);
        etFirstName.setError(null);
        etLastName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etMobile.setError(null);

        if (imageUrl == null || imageUrl.isEmpty()) {
            Toast.makeText(context, "Select an image for profile picture", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (DataHandler.getInstance().getUserType() == IKeyConstants.USER_TYPE_COMPANY) {
            if (companyName.isEmpty()) {
                etCompanyName.setError("Enter company name");
                return false;
            }

            if (!Utils.isNameValid(companyName)) {
                etCompanyName.setError("Enter a valid company name");
                return false;
            }
        }

        if (firstName.isEmpty()) {
            etFirstName.setError("Enter first name");
            return false;
        }

        if (!Utils.isNameValid(firstName)) {
            etFirstName.setError("Enter a valid first name");
            return false;
        }

        if (lastName.isEmpty()) {
            etLastName.setError("Enter last name");
            return false;
        }

        if (!Utils.isNameValid(lastName)) {
            etLastName.setError("Enter a valid last name");
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

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Minimum password length is " + MIN_PASSWORD_LENGTH);
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

        if (address.isEmpty()) {
            tvCity.setError("Select city");
            return false;
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
            jsonObject.put("image", imageUrl);
            jsonObject.put("company_name", companyName);
            jsonObject.put("first_name", firstName);
            jsonObject.put("last_name", lastName);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("country_code", countryCodePicker.getSelectedCountryCode());
            jsonObject.put("country", countryCodePicker.getSelectedCountryNameCode());
            jsonObject.put("mob_no", mobile);
            jsonObject.put("city", address);
            jsonObject.put("lat", latitude);
            jsonObject.put("lng", longitude);
            jsonObject.put("user_type", DataHandler.getInstance().getUserType() + IKeyConstants.EMPTY);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "registerUser(): jsonObject=" + jsonObject);
        new AQuery(context).post(IUrlConstants.REGISTER_PART_1, jsonObject, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                Log.d(TAG, "registerUser(): url= " + url + ", json= " + json + ", errorCode=" + status.getCode() + ", error=" + status.getError());
                if (json != null) {
                    try {
                        String strStatus = json.getString(IKeyConstants.STATUS);
                        if (IKeyConstants.SUCCESS.equalsIgnoreCase(strStatus)) {
                            Toast.makeText(context, "You have registered successfully. Please provide more details about you so that we can give you projects you want.", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, SignUpStep2Activity.class);
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
        });
    }
}
