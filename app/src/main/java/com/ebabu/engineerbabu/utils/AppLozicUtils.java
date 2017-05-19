//package com.ebabu.engineerbabu.utils;
//
//import android.content.Context;
//
//import com.applozic.mobicomkit.ApplozicClient;
//import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
//import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
//import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
//import com.applozic.mobicomkit.api.account.user.User;
//import com.applozic.mobicomkit.api.account.user.UserLoginTask;
//import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
//import com.ebabu.tooreestcustomer.R;
//
///**
// * Created by hp on 03/10/2016.
// */
//public class AppLozicUtils {
//
//    private Context context;
//    private static AppLozicUtils appLozicUtils = null;
//
//
//    private AppLozicUtils(Context context) {
//        this.context = context;
//    }
//
//    public static AppLozicUtils getInstance(Context context) {
//        if (appLozicUtils == null) {
//            appLozicUtils = new AppLozicUtils(context);
//        }
//        return appLozicUtils;
//    }
//
//    UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {
//
//        @Override
//        public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//            //After successful registration with Applozic server the callback will come here
//            //ApplozicSetting.getInstance(context).enableRegisteredUsersContactCall();//To enable the applozic Registered Users Contact Note:for disable that you can comment this line of code
//            managePushNotifications();
//            setupAppLozicConfig();
//        }
//
//        @Override
//        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//            //If any failure in registration the callback  will come here
//        }
//    };
//
//    public void loginInAppLozic(Context context) {
//        MobiComUserPreference userPreference = MobiComUserPreference.getInstance(context);
//        if (!userPreference.isRegistered()) {
//            User user = new User();
//            user.setUserId(AppPreference.getInstance(context).getEmail()); //userId it can be any unique user identifier
//            String fullname = AppPreference.getInstance(context).getFirstName();
//            user.setDisplayName(fullname); //displayName is the name of the user which will be shown in chat messages
//            user.setEmail(AppPreference.getInstance(context).getEmail()); //optional
//            user.setImageLink(AppPreference.getInstance(context).getProfileImage());//optional,pass your image link
//            user.setRoleName(context.getString(R.string.app_name));
//            new UserLoginTask(user, listener, context).execute((Void) null);
//        }
//    }
//
//    private void managePushNotifications() {
//        if (MobiComUserPreference.getInstance(context).isRegistered()) {
//
//            PushNotificationTask pushNotificationTask = null;
//            PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
//                @Override
//                public void onSuccess(RegistrationResponse registrationResponse) {
//
//                }
//
//                @Override
//                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                }
//
//            };
//
//            pushNotificationTask = new PushNotificationTask(AppPreference.getInstance(context).getFcmToken(), listener, context);
//            pushNotificationTask.execute((Void) null);
//
//        }
//    }
//
//    private void setupAppLozicConfig() {
//        ApplozicSetting.getInstance(context).showStartNewGroupButton()
//                .setCompressedImageSizeInMB(2)
//                .enableImageCompression()
//                .hideUserProfileFragment()
//                .hideStartNewGroupButton()
//                .hideInviteFriendsButton()
//                .setHideGroupAddButton(true)
//                .setHideGroupExitButton(true)
//                .setHideGroupNameEditButton(true)
//                .hideStartNewButton()
//                .setSentMessageBackgroundColor(R.color.accentColor)
//                .setSentMessageBorderColor(R.color.accentColor)
//                .setSendButtonBackgroundColor(R.color.primaryColor)
//                .setChatBackgroundColorOrDrawableResource(R.color.light_gray)
//                .hideStartNewFloatingActionButton();
//        ApplozicClient.getInstance(context).enableNotification();
//        ApplozicClient.getInstance(context).setContextBasedChat(true);
//
//    }
//}
