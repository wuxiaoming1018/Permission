package com.android.ming.lib_permission.annotation;

import com.android.ming.lib_permission.util.PermissionUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionCanceled {
    int requestCode() default PermissionUtils.DEFAULT_REQUEST_CODE;
}
