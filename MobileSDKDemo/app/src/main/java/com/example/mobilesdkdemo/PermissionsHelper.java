package com.example.mobilesdkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsHelper {

    private static int REQUEST_WRITE_STORAGE_REQUEST_CODE = 100;
    private static String[] audioCallPermission = {Manifest.permission.RECORD_AUDIO};
    private static String[] videoCallPermissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};


    public static boolean hasAudioCallPermission(Context context){
        return (ContextCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasVideoCallPermissions(Context context){
        return (ContextCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    public static void requestAudioCallPermission(Activity activity){
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasAudioCallPermission(activity)) {
            return;
        }

        ActivityCompat.requestPermissions(activity,
                audioCallPermission, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }


    public static void requestVideoCallPermissions(Activity activity){
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasVideoCallPermissions(activity)) {
            return;
        }

        ActivityCompat.requestPermissions(activity,
                videoCallPermissions, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }


}
