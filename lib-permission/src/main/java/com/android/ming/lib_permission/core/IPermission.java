package com.android.ming.lib_permission.core;

public interface IPermission {
    /**
     * 已经授权
     */
    void allowed();

    /**
     * 取消授权
     */
    void canceled();

    /**
     * 被拒绝了，点击不再提示
     */
    void denied();
}
