package com.zsh.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.zsh.mvvm.db.AppDatabase;
import com.zsh.mvvm.network.NetworkApi;
import com.zsh.mvvm.ui.activity.ActivityManager;
import com.zsh.mvvm.utils.MVUtils;

import java.util.HashMap;

/**
 * 作者：created by zsh19 on 2024/5/13 20:32
 * 邮箱：zsh1980794141@126.com
 */
public class BaseApplication extends Application {
    // 数据库
    public static AppDatabase db;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // 网络服务初始化
        NetworkApi.init(new NetworkRequiredInfo(this));
        context = getApplicationContext();
        // MMKV初始化
        String initialize = MMKV.initialize(this);
        // 工具类初始化
        MVUtils.getInstance();
        // 查看缓存文件的位置
        System.out.println("MMKV INIT " + initialize);
        // 创建本地数据库
        db = AppDatabase.getInstance(this);
        // 腾讯WebView初始化
        initX5WebView();
    }

    public static Context getContext() {
        return context;
    }

    public static AppDatabase getDb() {
        return db;
    }

    public static ActivityManager getActivityManager() {
        return ActivityManager.getInstance();
    }

    private void initX5WebView() {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        // 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean b) {
                // x5 内核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
                Log.d("app", " onViewInitFinished is " + b);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        // x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
