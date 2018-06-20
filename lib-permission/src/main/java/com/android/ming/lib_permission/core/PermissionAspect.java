package com.android.ming.lib_permission.core;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.ming.lib_permission.PermissionActivity;
import com.android.ming.lib_permission.annotation.Permission;
import com.android.ming.lib_permission.annotation.PermissionCanceled;
import com.android.ming.lib_permission.annotation.PermissionDenied;
import com.android.ming.lib_permission.util.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    private static final String TAG = "PermissionAspect";

    @Pointcut("exceution(@com.android.ming.lib_permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void requestPermission(Permission permission) {

    }

    @Around("requestPermission(permission)")
    public void aroundJointPoint(final ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        Context context = null;
        final Object obj = joinPoint.getThis();
        if (joinPoint.getThis() instanceof Context) {
            context = (Context) obj;
        } else if (joinPoint.getThis() instanceof Fragment) {
            context = ((Fragment) obj).getActivity();
        } else if (joinPoint.getThis() instanceof android.app.Fragment) {
            context = ((android.app.Fragment) obj).getActivity();
        }

        if (context == null || permission == null) {
            Log.d(TAG,"aroundJointPoint error");
        }
        final Context finalContext = context;
        PermissionActivity.requestPermission(context, permission.value(), permission.requestCode(), new IPermission() {
            @Override
            public void allowed() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void canceled() {
                PermissionUtils.invokeAnnotation(obj, PermissionCanceled.class);
            }

            @Override
            public void denied() {
                PermissionUtils.invokeAnnotation(obj, PermissionDenied.class);
                PermissionUtils.goToMenu(finalContext);
            }
        });
    }
}
