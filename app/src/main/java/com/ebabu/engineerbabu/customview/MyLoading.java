package com.ebabu.engineerbabu.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ebabu.engineerbabu.R;


/**
 * Created by hp on 05-11-2015.
 */
public class MyLoading {

    private Context context;
    private Dialog dialog;

    public MyLoading(Context context) {
        this.context = context;
    }

    public void show(String message) {
        dialog = new Dialog(context);
        try {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_loading, null);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            tvMessage.setText(message);
            dialog.setContentView(view);
            dialog.getWindow().setLayout(-1, -2);
            dialog.setCancelable(true);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}


