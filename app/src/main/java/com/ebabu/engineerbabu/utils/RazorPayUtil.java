//package com.ebabu.engineerbabu.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Toast;
//
//import com.razorpay.Checkout;
//
//import org.json.JSONObject;
//
///**
// * Created by hp on 07/05/2016.
// */
//public class RazorPayUtil {
//    private final static String TAG = RazorPayUtil.class.getSimpleName();
//    private Context context;
//    private int amount;
//    private String transactionId;
//
//    public RazorPayUtil(Context context, String transactionId, int amount) {
//        this.context = context;
//        this.transactionId = transactionId;
//        this.amount = amount;
//
//    }
//
//    public void startPayment() {
//        //Jingadala Test API Key
//        final String public_key = "rzp_test_aX6cSdMxFaIqHY";
//
//        //Jingadala Live API Key
//        //final String public_key = "rzp_live_t2x7Lu12rV6RBP";
//
//        final Checkout co = new Checkout();
//        co.setPublicKey(public_key);
//
//
//        try {
//            AppPreference appPreference = AppPreference.getInstance(context);
//            JSONObject options = new JSONObject("{" +
//                    "description: 'Pay using RazorPay'," +
//                    "image: 'https://s29.postimg.org/9axlx53qf/ic_launcher.png'," +
//                    "currency: 'INR'}"
//            );
//
//            options.put("amount", amount);
//            options.put("name", transactionId);
//            options.put("prefill", new JSONObject("{email: '" + appPreference.getEmail() + "', contact: '" + appPreference.getMobileNum() + "', name: '" + appPreference.getFirstName() + "'}"));
//
//            co.open((Activity) context, options);
//
//        } catch (Exception e) {
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//
//
//}
