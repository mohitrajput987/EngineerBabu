package com.ebabu.engineerbabu.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.ebabu.engineerbabu.activity.LoginActivity;
import com.ebabu.engineerbabu.constant.IKeyConstants;


/**
 * Created by hp on 22/09/2016.
 */
public class DialogUtils {
    public static void openDialogToLogout(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to logout?");
        builder.setTitle("Logout");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.logout(context);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void openAlertToShowMessage(String message, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void openAlertToShowMessage(String title, String message, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static boolean checkIsLoginAndOpenDialog(final Context context) {
        if (!AppPreference.getInstance(context).isLoggedIn()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("LOGIN");
            builder.setMessage("You need to login to perform this action.");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra(IKeyConstants.FROM_INSIDE, true);
                    context.startActivity(intent);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            return true;
        }
        return false;
    }
}
