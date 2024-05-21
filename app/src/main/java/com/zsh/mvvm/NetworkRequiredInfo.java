package com.zsh.mvvm;

import android.app.Application;

import com.zsh.mvvm.network.INetworkRequiredInfo;

/**
 * 作者：created by zsh19 on 2024/5/13 20:28
 * 邮箱：zsh1980794141@126.com
 */


public class NetworkRequiredInfo implements INetworkRequiredInfo {

    private final Application application;

    public NetworkRequiredInfo(Application application) {
        this.application = application;
    }

    /**
     * 版本名
     */
    @Override
    public String getAppVersionName() {
        return "MVVMDemo";
    }

    /**
     * 版本号
     */
    @Override
    public String getAppVersionCode() {
        return String.valueOf(1.0);
    }

    /**
     * 是否为debug
     */
    @Override
    public boolean isDebug() {
        return true;
    }

    /**
     * 应用全局上下文
     */
    @Override
    public Application getApplicationContext() {
        return application;
    }
}

