package com.android.ming.lib_permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.android.ming.lib_permission.core.IPermission;
import com.android.ming.lib_permission.util.PermissionUtils;

public class PermissionActivity extends Activity {

    private static final String PARAM_PERMISSION = "param_permission";
    private static final String PARAM_REQUEST_CODE = "param_request_code";

    private String[] mPermissions;
    private int mRequestCode;
    private static IPermission permissionListener;


    public static void requestPermission(Context context, String[] permissions, int requestCode, IPermission iPermission) {
        permissionListener = iPermission;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PARAM_PERMISSION, permissions);
        bundle.putInt(PARAM_REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tim_permission_layout);
        mPermissions = getIntent().getStringArrayExtra(PARAM_PERMISSION);
        mRequestCode = getIntent().getIntExtra(PARAM_REQUEST_CODE, -1);
        if (mPermissions == null || mRequestCode < 0 || permissionListener == null) {
            this.finish();
            return;
        }
        //检查是否授权
        if (PermissionUtils.hasPermission(this,mPermissions)) {
            permissionListener.allowed();
            finish();
            return;
        }
        ActivityCompat.requestPermissions(this,mPermissions,mRequestCode);
    }

    @Override
    public void finish() {
        super.finish();
        //去掉activity退出时动画
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限请求成功
        if(PermissionUtils.verifyPermission(this,grantResults)){
            permissionListener.allowed();
            finish();
            return;
        }

        //用户点击了不再显示
        if(!PermissionUtils.showRequestPermissionRationale(this,permissions)){
            permissionListener.denied();
            finish();
            return;
        }

        //用户取消
        permissionListener.canceled();
        finish();
    }
}
