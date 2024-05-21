package com.zsh.mvvm.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.zsh.mvvm.BR;

/**
 * 作者：created by zsh19 on 2024/5/13 15:05
 * 邮箱：zsh1980794141@126.com
 */
public class User extends BaseObservable {

    public String account;
    public String pwd;

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        // notifyChange();// 通知改变 所有参数改变
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }

    public User(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }
}

