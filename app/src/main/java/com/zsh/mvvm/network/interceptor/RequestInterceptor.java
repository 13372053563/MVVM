package com.zsh.mvvm.network.interceptor;

import com.zsh.mvvm.network.INetworkRequiredInfo;
import com.zsh.mvvm.network.utils.DateUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：created by zsh19 on 2024/5/13 20:10
 * 邮箱：zsh1980794141@126.com
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private INetworkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo) {
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String nowDateTime = DateUtil.getDateTime();
        // 构建器
        Request.Builder builder = chain.request().newBuilder();
        // 添加使用环境
        builder.addHeader("os", "android");
        // 添加版本号
        builder.addHeader("appVersionCode", this.iNetworkRequiredInfo.getAppVersionCode());
        // 添加版本名
        builder.addHeader("appVersionName", this.iNetworkRequiredInfo.getAppVersionName());
        // 添加日期时间
        builder.addHeader("datetime", nowDateTime);
        // 返回
        return chain.proceed(builder.build());
    }
}
