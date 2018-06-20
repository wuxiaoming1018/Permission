package com.android.ming.lib_permission.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SimpleArrayMap;

import com.android.ming.lib_permission.PermissionActivity;

public class PermissionUtils {
    public static final int DEFAULT_REQUEST_CODE = 0xABC1994;

    private static SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    /**
     * 检查是否需要请求权限
     *
     * @param context
     * @param permissions
     * @return false---需要  true---不需要
     */
    public static boolean hasPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasSelfPermission(Context context, String permission) {
        try {
            // ContextCompat.checkSelfPermission，主要用于检测某个权限是否已经被授予。
            // 方法返回值为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED
            // 当返回DENIED就需要进行申请授权了。
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 如果此SDK版本存在权限，则返回true
     *
     * @param permission
     * @return
     */
    private static boolean permissionExists(String permission) {
        Integer version = MIN_SDK_PERMISSIONS.get(permission);
        return version == null || Build.VERSION.SDK_INT >= version;
    }

    public static boolean verifyPermission(Context context, int[] grantResults) {
        if (grantResults == null&&grantResults.length==0) {
            return false;
        }
        for (int result : grantResults) {
            if (result!= PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean showRequestPermissionRationale(Activity activity,String ...permissions){
        for (String permission : permissions) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                return true;
            }
        }
        return false;
    }
}
