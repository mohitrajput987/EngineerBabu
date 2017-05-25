package com.ebabu.engineerbabu.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.activity.LoginActivity;
import com.ebabu.engineerbabu.activity.SplashActivity;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.helpshift.support.Support;
import com.rey.material.app.DialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by hp on 10/09/2016.
 */
public class Utils {
    public static final String ARRAY_SERVICE_TYPE[] = {"Physical", "Virtual"};
    public static final String ARRAY_DAYS[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static final String SOS_MESSAGE = "Hey!!! I'm in trouble, having major health problem.\nPlease reach me ASAP";
    private static String mDob;
    public static final SimpleDateFormat displayTimeFormat = new SimpleDateFormat("hh:mm a");
    public static final SimpleDateFormat onlyDateMonth = new SimpleDateFormat("dd MMM");
    public static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd MMM, yyyy");
    public static final SimpleDateFormat completeDisplayDateFormat = new SimpleDateFormat("dd MMM, yyyy hh:mm a");
    public static final SimpleDateFormat numericDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat completeTimestampFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    public static final SimpleDateFormat dateDashFormat = new SimpleDateFormat("dd-MMM-yyyy");
    public static final SimpleDateFormat fullTimestamp24hours = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE_PICKUP = 999;
    public static final String[] RATING_TEXT = {"Hated It", "Didn't like it", "Just OK", "Liked It", "Awesome"};

    public final static String[] PLATFORM_NAMES = {"Web", "Android", "iPhone", "Graphics", "Content", "SMM", "SEO"};
    public final static int[] PLATFORM_IMAGES = {R.mipmap.web, R.mipmap.android, R.mipmap.iphone, R.mipmap.graphic, R.mipmap.content, R.mipmap.smm, R.mipmap.smm};

    public static Toolbar setUpToolbar(final Context context, String title) {
        final AppCompatActivity activity = (AppCompatActivity) context;
        Toolbar actionBarToolbar = (Toolbar) activity.findViewById(R.id.toolbar_actionbar);
        activity.setSupportActionBar(actionBarToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        //actionBarToolbar.setBackgroundResource(backgroundResource);
        actionBarToolbar.setNavigationIcon(R.mipmap.back);
        actionBarToolbar.setNavigationContentDescription(title);
        actionBarToolbar.setTitle("");
        actionBarToolbar.setSubtitle("");
        CustomTextView tvTitle = (CustomTextView) actionBarToolbar.findViewById(R.id.toolbar_title);
        tvTitle.setText(title);
        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return actionBarToolbar;
    }

    public static int getNotificationsIcon() {
        if (Build.VERSION.SDK_INT >= 21) {
            return R.mipmap.ic_launcher;
        } else {
            return R.mipmap.ic_launcher;
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = ((Activity) context).getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void openTimerPickerDialog(Context context, final TextView tvTime) {
        try {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            com.rey.material.app.Dialog.Builder builder = new com.rey.material.app.TimePickerDialog.Builder(R.style.MyTimePickerTheme, hour, minute) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    com.rey.material.app.TimePickerDialog dialog = (com.rey.material.app.TimePickerDialog) fragment.getDialog();
                    tvTime.setText(dialog.getFormattedTime(displayTimeFormat).toUpperCase());
                    fragment.dismiss();
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    fragment.dismiss();
                }
            };

            builder.positiveAction("OK")
                    .negativeAction("CANCEL");
            DialogFragment fragment = DialogFragment.newInstance(builder);

            fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openCalendarDialog(Context context, final TextView tvDob) {
        try {
            int mDay, mMonth, mYear, mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear;
            Calendar cal = Calendar.getInstance();
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            mMonth = cal.get(Calendar.MONTH);
            mYear = cal.get(Calendar.YEAR);


            mMinDay = mDay;
            mMinMonth = mMonth;
            mMinYear = mYear;

            mMaxDay = mDay;
            mMaxMonth = mMonth;
            mMaxYear = mYear + 1;

//            if (dob != null && !dob.isEmpty()) {
//                try {
//                    Calendar dobCal = Calendar.getInstance();
//                    dobCal.setTime(completeTimestampFormat.parse(dob));
//                    mDay = dobCal.get(Calendar.DAY_OF_MONTH);
//                    mMonth = dobCal.get(Calendar.MONTH);
//                    mYear = dobCal.get(Calendar.YEAR);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


            com.rey.material.app.Dialog.Builder builder = new com.rey.material.app.DatePickerDialog.Builder(mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear, mDay, mMonth, mYear) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    com.rey.material.app.DatePickerDialog dialog = (com.rey.material.app.DatePickerDialog) fragment.getDialog();
                    mDob = dialog.getFormattedDate(displayDateFormat);

                    tvDob.setText(dialog.getFormattedDate(displayDateFormat));
                    fragment.dismiss();
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    fragment.dismiss();
                }
            };

            builder.positiveAction("OK")
                    .negativeAction("CANCEL").style(R.style.MyCalendarTheme);
            DialogFragment fragment = DialogFragment.newInstance(builder);


            fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openCalendarDialog(Context context, final TextView tvDob, final SimpleDateFormat dateFormat) {
        try {
            int mDay, mMonth, mYear, mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear;
            Calendar cal = Calendar.getInstance();
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            mMonth = cal.get(Calendar.MONTH);
            mYear = cal.get(Calendar.YEAR);


            mMinDay = mDay;
            mMinMonth = mMonth;
            mMinYear = mYear;

            mMaxDay = mDay;
            mMaxMonth = mMonth;
            mMaxYear = mYear + 1;

//            if (dob != null && !dob.isEmpty()) {
//                try {
//                    Calendar dobCal = Calendar.getInstance();
//                    dobCal.setTime(completeTimestampFormat.parse(dob));
//                    mDay = dobCal.get(Calendar.DAY_OF_MONTH);
//                    mMonth = dobCal.get(Calendar.MONTH);
//                    mYear = dobCal.get(Calendar.YEAR);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


            com.rey.material.app.Dialog.Builder builder = new com.rey.material.app.DatePickerDialog.Builder(mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear, mDay, mMonth, mYear) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    com.rey.material.app.DatePickerDialog dialog = (com.rey.material.app.DatePickerDialog) fragment.getDialog();
                    mDob = dialog.getFormattedDate(dateFormat);

                    tvDob.setText(dialog.getFormattedDate(dateFormat));
                    fragment.dismiss();
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    fragment.dismiss();
                }
            };

            builder.positiveAction("OK")
                    .negativeAction("CANCEL").style(R.style.MyCalendarTheme);
            DialogFragment fragment = DialogFragment.newInstance(builder);


            fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isNameValid(String fullName) {
        String regx = "^[\\p{L} .'-]+$";

        if (fullName == null) {
            return false;
        } else {
            return Pattern.matches(regx, fullName);
        }
    }

    public static boolean isAlphabetsOnly(String fullName) {
        String regx = "[a-zA-Z]";

        if (fullName == null) {
            return false;
        } else {
            return Pattern.matches(regx, fullName);
        }
    }

    public static boolean isWebsiteValid(String website) {
        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        if (website == null) {
            return false;
        } else {
            return Pattern.matches(regex, website);
        }
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static boolean intToBoolean(int intValue) {
        return intValue == 0 ? false : true;
    }

    public static void logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        AppPreference.getInstance(context).clear();
        //MobiComUserPreference.getInstance(context).clearAll();
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return width;
    }

    public static void openPlayStoreToRateUs(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    public static void sendSMS(Context context, String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(context.getApplicationContext(), "Message sent to " + phoneNo,
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static void generateNotification(Context context, Map<String, String> data) {
        try {
            Bitmap largeBitmap = getBitmapFromUrl(context, data.get("image"));
            long when = System.currentTimeMillis();
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context.getApplicationContext());
            Intent resultIntent;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            resultIntent = new Intent(context.getApplicationContext(), SplashActivity.class);
            if (data.containsKey(IKeyConstants.NOTIFICATION_TYPE)) {
                resultIntent.putExtra(IKeyConstants.NOTIFICATION_TYPE, data.get(IKeyConstants.NOTIFICATION_TYPE));
            }
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(context.getApplicationContext())
                    .setColor(context.getResources().getColor(R.color.primaryColor))
                    .setSmallIcon(Utils.getNotificationsIcon())
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(largeBitmap)
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("message")).build();

            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
            notification.priority = Notification.PRIORITY_MAX;
            notificationManager.notify((int) when, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromUrl(Context context, String myUrl) {
        URL url = null;
        try {
            if (myUrl != null && !myUrl.isEmpty()) {
                url = new URL(myUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap image = null;
        try {
            if (url != null) {
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image == null) {
            image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }

        return image;
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static String capitalizeString(String string) {
        if (string != null) {
            return (string.charAt(0) + IKeyConstants.EMPTY).toUpperCase() + string.substring(1).replaceAll("_", IKeyConstants.SPACE);
        }
        return IKeyConstants.EMPTY;
    }

//    public static void setAppLanguage(Context context, String language) {
//        Resources resources = context.getResources();
//        if (language != null) {
//            String languageArray[] = resources.getStringArray(R.array.array_language);
//            if (language.equalsIgnoreCase(languageArray[1])) {
//                language = "en";
//            } else if (language.equalsIgnoreCase(languageArray[2])) {
//                language = "hi";
//            } else if (language.equalsIgnoreCase(languageArray[3])) {
//                language = "de";
//            } else if (language.equalsIgnoreCase(languageArray[4])) {
//                language = "fr";
//            }
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//            Configuration config = new Configuration();
//            config.locale = locale;
//            resources.updateConfiguration(config,
//                    resources.getDisplayMetrics());
//        }
//    }


    public static int getPercentAmount(float amount) {
        return (int) Math.ceil((((IKeyConstants.SERVICE_TAX_PERCENT * amount) / 100)));
    }
//    public static boolean isGooglePlayServicesAvailable(Activity activity) {
//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
//        if (status != ConnectionResult.SUCCESS) {
//            if (googleApiAvailability.isUserResolvableError(status)) {
//                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
//            }
//            return false;
//        }
//        return true;
//    }

    public static void startHelpshiftFaq(Context context) {
        HashMap customMetadata = new HashMap();
        AppPreference appPreference = AppPreference.getInstance(context);

        //Helpshift.login(appPreference.getUserId(), appPreference.getFirstName(), appPreference.getEmail());
        customMetadata.put("UserType", appPreference.getUserType());
        customMetadata.put("Name", appPreference.getFirstName());
        customMetadata.put("Email", appPreference.getEmail());
        customMetadata.put("Mobile", appPreference.getMobileNum());

        HashMap config = new HashMap();
        //config.put("gotoConversationAfterContactUs", true);
        config.put("enableContactUs", Support.EnableContactUs.ALWAYS);
        //config.put("hideNameAndEmail", true);
        //config.put("requireEmail", false);
        config.put(Support.CustomMetadataKey, customMetadata);
        Support.showFAQs((Activity) context);
    }

    public static String generateOrderId(Context context) {
        AppPreference appPreference = AppPreference.getInstance(context);
        String orderId = "JK-" + appPreference.getUserId() + "-" + System.currentTimeMillis();
        return orderId;
    }


    public static String readRawFile(Context context, int resourceId) {
        Resources res = context.getResources();
        InputStream inputStream = res.openRawResource(resourceId);

        try {
            byte[] byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray);
            return new String(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IKeyConstants.EMPTY;
    }


    public static void startAddressActivity(Context context, int requestCode) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build((Activity) context);
            ((Activity) context).startActivityForResult(intent, requestCode);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public static boolean isValidImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith(IKeyConstants.HTTP) && (imageUrl.endsWith("jpg") || (imageUrl.endsWith("jpeg") || (imageUrl.endsWith("png"))))) {
            return true;
        }
        return false;
    }

//    public static List<Platform> getPlatformList() {
//        List<Platform> listPlatforms = new ArrayList<>();
//        for (int i = 0; i < PLATFORM_NAMES.length; i++) {
//            listPlatforms.add(new Platform(PLATFORM_NAMES[i], PLATFORM_IMAGES[i]));
//        }
//        return listPlatforms;
//    }

    public static Bitmap getBitmapFromFilePath(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }
}

//Hello