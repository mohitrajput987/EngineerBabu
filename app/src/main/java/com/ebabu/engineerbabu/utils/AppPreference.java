package com.ebabu.engineerbabu.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hp on 16/09/2016.
 */
public class AppPreference {
    private Context context;
    private SharedPreferences sharedPreferences;

    private final static String PREFERENCE_NAME = "AppPreference";
    public final static String USER_TYPE = "user_type";
    public final static String FIRST_NAME = "user_fname";
    public final static String LAST_NAME = "user_lname";

    public final static String SECRET_KEY = "user_auth_token";
    public final static String IS_OTP_SCREEN = "IS_OTP_SCREEN";

    public final static String USER_ID = "user_id";
    public final static String COMPANY_NAME = "user_company_name";
    public final static String MOBILE_NUM = "user_mobile";
    public final static String EMAIL = "user_email";
    public final static String PASSWORD = "user_password";
    public final static String COUNTRY = "user_country";
    public final static String CITY = "user_city";
    public final static String PROFILE_IMAGE = "user_image";

    public final static String FB_LINK = "user_fb_url";
    public final static String TWITTER_LINK = "user_twitter_url";
    public final static String LINKEDIN_LINK = "user_linkdin_url";
    public final static String FCM_TOKEN = "FCM_TOKEN";
    public final static String IS_CHAT_NOTI_ON = "IS_CHAT_NOTI_ON";
    public final static String IS_OTHER_NOTI_ON = "IS_OTHER_NOTI_ON";

    public final static String USER_TEAM_SIZE = "user_team_size";
    public final static String USER_MIN_PROJECT_AMOUNT = "user_min_project_amount";
    public final static String USER_MAX_PROJECT_AMOUNT = "user_max_project_amount";
    public final static String USER_URL = "user_url";
    public final static String USER_REG_DATE = "user_reg_date";
    public final static String USER_REG_STATUS = "user_reg_status";
    public final static String USER_PROFILE_COMPLETED = "user_profile_completed";
    public final static String USER_STATUS = "user_status";
    public final static String USER_SUBSCRIPTION_TYPE = "user_subscription_type";
    public final static String USER_EMAIL_CONFIRMED = "user_email_confirmed";
    public final static String USER_SUBSCRIPTION_VALID_TILL = "user_subscription_valid_till";
    public final static String USER_SUBSCRIPTION_STATUS = "user_subscription_status";
    public final static String STAT_WEBSITE_VISITS = "stat_website_visits";
    public final static String STAT_PROFILE_VIEWS = "stat_profile_views";
    public final static String USER_ABOUT = "user_about";


    public AppPreference(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance(Context context) {
        return new AppPreference(context);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getUserType() {
        String userType = sharedPreferences.getString(USER_TYPE, null);
        return userType;
    }

    public void setUserType(String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }

    public String getSecretKey() {
        String userType = sharedPreferences.getString(SECRET_KEY, "abcd");
        return userType;
    }

    public void setSecretKey(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SECRET_KEY, secretKey);
        editor.commit();
    }

    public boolean isOtpScreen() {
        boolean isOtpScreen = sharedPreferences.getBoolean(IS_OTP_SCREEN, false);
        return isOtpScreen;
    }

    public void setIsOtpScreen(boolean isOtpScreen) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_OTP_SCREEN, isOtpScreen);
        editor.commit();
    }

    public String getUserId() {
        String userType = sharedPreferences.getString(USER_ID, null);
        return userType;
    }

    public void setUserId(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, secretKey);
        editor.commit();
    }

    public String getCompanyName() {
        String userType = sharedPreferences.getString(COMPANY_NAME, null);
        return userType;
    }

    public void setCompanyName(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COMPANY_NAME, secretKey);
        editor.commit();
    }

    public String getFirstName() {
        String userType = sharedPreferences.getString(FIRST_NAME, null);
        return userType;
    }

    public void setFirstName(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_NAME, secretKey);
        editor.commit();
    }

    public String getLastName() {
        String userType = sharedPreferences.getString(LAST_NAME, null);
        return userType;
    }

    public void setLastName(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_NAME, secretKey);
        editor.commit();
    }

    public String getMobileNum() {
        String userType = sharedPreferences.getString(MOBILE_NUM, null);
        return userType;
    }

    public void setMobileNum(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_NUM, secretKey);
        editor.commit();
    }

    public String getEmail() {
        String userType = sharedPreferences.getString(EMAIL, null);
        return userType;
    }

    public void setEmail(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, secretKey);
        editor.commit();
    }


    public String getPassword() {
        String userType = sharedPreferences.getString(PASSWORD, null);
        return userType;
    }

    public void setPassword(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, secretKey);
        editor.commit();
    }


    public String getCountry() {
        String userType = sharedPreferences.getString(COUNTRY, null);
        return userType;
    }

    public void setCountry(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY, secretKey);
        editor.commit();
    }

    public String getCity() {
        String userType = sharedPreferences.getString(CITY, null);
        return userType;
    }

    public void setCity(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CITY, secretKey);
        editor.commit();
    }

    public String getProfileImage() {
        String userType = sharedPreferences.getString(PROFILE_IMAGE, null);
        return userType;
    }

    public void setProfileImage(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_IMAGE, secretKey);
        editor.commit();
    }

    public String getFbLink() {
        String userType = sharedPreferences.getString(FB_LINK, null);
        return userType;
    }

    public void setFbLink(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FB_LINK, secretKey);
        editor.commit();
    }


    public String getTwitterLink() {
        String userType = sharedPreferences.getString(TWITTER_LINK, null);
        return userType;
    }

    public void setTwitterLink(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TWITTER_LINK, secretKey);
        editor.commit();
    }

    public String getLinkedinLink() {
        String userType = sharedPreferences.getString(LINKEDIN_LINK, null);
        return userType;
    }

    public void setLinkedinLink(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LINKEDIN_LINK, secretKey);
        editor.commit();
    }


    public String getFcmToken() {
        String userType = sharedPreferences.getString(FCM_TOKEN, null);
        return userType;
    }

    public void setFcmToken(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FCM_TOKEN, secretKey);
        editor.commit();
    }

    public boolean isChatNotiOn() {
        boolean isOtpScreen = sharedPreferences.getBoolean(IS_CHAT_NOTI_ON, true);
        return isOtpScreen;
    }

    public void setChatNotiOn(boolean isOtpScreen) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_CHAT_NOTI_ON, isOtpScreen);
        editor.commit();
    }

    public boolean isOtherNotiOn() {
        boolean isOtpScreen = sharedPreferences.getBoolean(IS_OTHER_NOTI_ON, true);
        return isOtpScreen;
    }

    public void setOtherNotiOn(boolean isOtpScreen) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_OTHER_NOTI_ON, isOtpScreen);
        editor.commit();
    }

    public int getUserTeamSize() {
        return sharedPreferences.getInt(USER_TEAM_SIZE, 0);
    }

    public void setUserTeamSize(int userTeamSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_TEAM_SIZE, userTeamSize);
        editor.commit();
    }

    public int getMinProjectAmnt() {
        return sharedPreferences.getInt(USER_MIN_PROJECT_AMOUNT, 0);
    }

    public void setMinProjectAmnt(int userTeamSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_MIN_PROJECT_AMOUNT, userTeamSize);
        editor.commit();
    }

    public int getMaxProjectAmnt() {
        return sharedPreferences.getInt(USER_MAX_PROJECT_AMOUNT, 0);
    }

    public void setMaxProjectAmnt(int userTeamSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_MAX_PROJECT_AMOUNT, userTeamSize);
        editor.commit();
    }

    public String getUserUrl() {
        String userType = sharedPreferences.getString(USER_URL, null);
        return userType;
    }

    public void setUserUrl(String userUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_URL, userUrl);
        editor.commit();
    }

    public String getUserRegDate() {
        String userType = sharedPreferences.getString(USER_REG_DATE, null);
        return userType;
    }

    public void setUserRegDate(String userUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_REG_DATE, userUrl);
        editor.commit();
    }

    public int getUserProfileCompletion() {
        return sharedPreferences.getInt(USER_PROFILE_COMPLETED, 0);
    }

    public void setUserProfileCompletion(int userTeamSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_PROFILE_COMPLETED, userTeamSize);
        editor.commit();
    }

    public String getUserStatus() {
        String userType = sharedPreferences.getString(USER_STATUS, null);
        return userType;
    }

    public void setUserStatus(String userUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_STATUS, userUrl);
        editor.commit();
    }

    public String getUserAbout() {
        String userType = sharedPreferences.getString(USER_ABOUT, null);
        return userType;
    }

    public void setUserAbout(String userUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ABOUT, userUrl);
        editor.commit();
    }

    public int getUserSubscriptionType() {
        return sharedPreferences.getInt(USER_SUBSCRIPTION_TYPE, 0);
    }

    public void setUserSubscriptionType(int userTeamSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_SUBSCRIPTION_TYPE, userTeamSize);
        editor.commit();
    }

    public int getUserEmailConfirmed() {
        return sharedPreferences.getInt(USER_EMAIL_CONFIRMED, 0);
    }

    public void setUserEmailConfirmed(int userEmailConfirmed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_EMAIL_CONFIRMED, userEmailConfirmed);
        editor.commit();
    }

    public String getUserSubscriptionValidTill() {
        String userType = sharedPreferences.getString(USER_SUBSCRIPTION_VALID_TILL, null);
        return userType;
    }

    public void setUserSubscriptionValidTill(String userUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_SUBSCRIPTION_VALID_TILL, userUrl);
        editor.commit();
    }

    public int getStatWebsiteVisits() {
        return sharedPreferences.getInt(STAT_WEBSITE_VISITS, 0);
    }

    public void setStatWebsiteVisits(int userEmailConfirmed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STAT_WEBSITE_VISITS, userEmailConfirmed);
        editor.commit();
    }

    public int getUserRegStatus() {
        return sharedPreferences.getInt(USER_REG_STATUS, 0);
    }

    public void setUserRegStatus(int userEmailConfirmed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_REG_STATUS, userEmailConfirmed);
        editor.commit();
    }

    public int getStatProfileViews() {
        return sharedPreferences.getInt(STAT_PROFILE_VIEWS, 0);
    }

    public void setStatProfileViews(int userEmailConfirmed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STAT_PROFILE_VIEWS, userEmailConfirmed);
        editor.commit();
    }

    public int getUserSubscriptionStatus() {
        return sharedPreferences.getInt(USER_SUBSCRIPTION_STATUS, 0);
    }

    public void setUserSubscriptionStatus(int userEmailConfirmed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_SUBSCRIPTION_STATUS, userEmailConfirmed);
        editor.commit();
    }

    public boolean isLoggedIn() {
        if (sharedPreferences.contains(SECRET_KEY)) {
            return true;
        }

        return false;
    }
}
