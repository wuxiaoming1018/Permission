package com.android.ming.permission;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.ming.lib_permission.annotation.Permission;
import com.android.ming.lib_permission.annotation.PermissionCanceled;
import com.android.ming.lib_permission.annotation.PermissionDenied;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_all).setOnClickListener(this);
        findViewById(R.id.btn_all_exclude).setOnClickListener(this);
        findViewById(R.id.btn_one_permission).setOnClickListener(this);
        findViewById(R.id.btn_two_permission).setOnClickListener(this);
        findViewById(R.id.btn_request_200).setOnClickListener(this);
        findViewById(R.id.btn_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all:
                requestAll();
                break;
            case R.id.btn_all_exclude:
                requestAllExclue();
                break;
            case R.id.btn_one_permission:
                requestOnePermission();
                break;
            case R.id.btn_two_permission:
                requestTwoPermission();
                break;
            case R.id.btn_request_200:
                requestRequest200();
                break;
            case R.id.btn_service:
                requestService();
                break;
        }
    }

    private void requestService() {
        Intent intent =new Intent(this,MyService.class);
        startService(intent);
    }

    @Permission(value = {Manifest.permission.ACCESS_FINE_LOCATION},requestCode = 200)
    private void requestRequest200() {
        Toast.makeText(this, "请求定位权限成功，200", Toast.LENGTH_SHORT).show();
    }

    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    private void requestTwoPermission() {
        Toast.makeText(this, "请求两个权限成功（写和相机）", Toast.LENGTH_SHORT).show();
    }

    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void requestOnePermission() {
        Toast.makeText(this, "请求一个权限成功（写权限）", Toast.LENGTH_SHORT).show();
    }

    private void requestAllExclue() {

    }

    private void requestAll() {

    }

    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    private void requestWrite() {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }

    @PermissionCanceled(requestCode = 200)
    private void cancelCode200(){
        Toast.makeText(this, "cancel__200", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(requestCode = 200)
    private void denyCode200(){
        Toast.makeText(this, "deny__200", Toast.LENGTH_SHORT).show();
    }

    @PermissionCanceled()
    private void cancel() {
        Log.i(TAG, "writeCancel: " );
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied()
    private void deny() {
        Log.i(TAG, "writeDeny:");
        Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show();
    }
}
