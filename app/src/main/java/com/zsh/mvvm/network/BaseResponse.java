package com.zsh.mvvm.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 作者：created by zsh19 on 2024/5/13 20:05
 * 邮箱：zsh1980794141@126.com
 */


public class BaseResponse {

    // 返回码
    @SerializedName("res_code")
    @Expose
    public Integer responseCode;

    // 返回的错误信息
    @SerializedName("res_error")
    @Expose
    public String responseError;
}

