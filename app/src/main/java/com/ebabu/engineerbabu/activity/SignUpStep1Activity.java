package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.DataHandler;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomEditText;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.ebabu.engineerbabu.utils.ImageUtils;
import com.ebabu.engineerbabu.utils.Utils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.hbb20.CountryCodePicker;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpStep1Activity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private CountryCodePicker countryCodePicker;
    private CustomTextView tvCity;
    private CircleImageView ivProfilePic;
    private CustomEditText etCompanyName;
    private String address;
    private double latitude, longitude;
    private ImageUtils imageUtils;

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
                Intent intent = new Intent(context, SignUpStep2Activity.class);
                startActivity(intent);
                break;

            case R.id.tv_city:
                Utils.startAddressActivity(context, Utils.PLACE_AUTOCOMPLETE_REQUEST_CODE_PICKUP);
                break;

            case R.id.iv_profile_pic:
                imageUtils.openDialogToChosePic();
                break;
        }
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
            } else {
                imageUtils.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
