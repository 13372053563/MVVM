package com.zsh.mvvm.network;

import android.app.Application;

/**
 * 作者：created by zsh19 on 2024/5/13 20:02
 * 邮箱：zsh1980794141@126.com
 */
public interface INetworkRequiredInfo {
    /**
     * 获取App版本名
     */
    String getAppVersionName();

    /**
     * 获取App版本号
     */
    String getAppVersionCode();

    /**
     * 判断是否为Debug模式
     */
    boolean isDebug();

    /**
     * 获取全局上下文参数
     */
    Application getApplicationContext();
}

