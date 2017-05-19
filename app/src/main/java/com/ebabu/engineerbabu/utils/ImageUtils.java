package com.ebabu.engineerbabu.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by hp on 29/09/2016.
 */
public class ImageUtils {
    private Context context;
    private ImageView imageView;
    private byte byteArray[] = null;
    private Bitmap bitmap;
    public static final int REQUEST_CODE_CAMERA = 1;
    private Uri mCameraImageUri;

    public ImageUtils(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                beginCrop(mCameraImageUri);
            } else if (requestCode == Crop.REQUEST_PICK) {
                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                new ImageCompressionTask(data).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(context.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start((Activity) context);
    }


    private class ImageCompressionTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private Intent intent;

        public ImageCompressionTask(Intent intent) {
            this.intent = intent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            if (intent != null) {
                Uri imageUri = Crop.getOutput(intent);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Bundle extras = intent.getExtras();
//                if (extras != null) {
//                    bitmap = extras.getParcelable("data");
//                }
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                    bitmap = getResizedBitmap(bitmap);
                    byteArray = Utils.convertBitmapToByteArray(bitmap);

                    Log.d("ImageUtils", "byteArray: " + byteArray);

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public void onSelectCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        mCameraImageUri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public static Bitmap getResizedBitmap(Bitmap source) {
        boolean isLandscape = source.getWidth() > source.getHeight();

        int newWidth, newHeight;
        if (isLandscape) {
            newWidth = 640;
            newHeight = Math.round(((float) newWidth / source.getWidth()) * source.getHeight());
        } else {
            newHeight = 480;
            newWidth = Math.round(((float) newHeight / source.getHeight()) * source.getWidth());
        }

        Bitmap result = Bitmap.createScaledBitmap(source, newWidth, newHeight, false);

        if (result != source)
            source.recycle();

        return result;
    }

    public void openDialogToChosePic() {
        if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
            if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write External Storage")) {
                return;
            }
        }

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Take picture using");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSelectCamera();
            }
        });

        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Crop.pickImage((Activity) context);
            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}
