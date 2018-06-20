package com.android.ming.permission;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.ming.lib_permission.annotation.Permission;
import com.android.ming.lib_permission.annotation.PermissionCanceled;
import com.android.ming.lib_permission.annotation.PermissionDenied;

public class MyService extends Service{
    private static final String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requestCamera();

        return super.onStartCommand(intent, flags, startId);
    }

    @Permission(Manifest.permission.CAMERA)
    private void requestCamera() {
        Toast.makeText(getApplicationContext(),"SERVICE中请求权限——通过",Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied()
    private void deny() {
        Log.i(TAG, "SERVICE中请求权限_writeDeny:" );
        Toast.makeText(this, "SERVICE中请求权限_deny", Toast.LENGTH_SHORT).show();

    }

    @PermissionCanceled()
    private void cancel() {
        Log.i(TAG, "SERVICE中请求权限_writeCancel: " );
        Toast.makeText(this, "SERVICE中请求权限_cancel", Toast.LENGTH_SHORT).show();
    }
}
