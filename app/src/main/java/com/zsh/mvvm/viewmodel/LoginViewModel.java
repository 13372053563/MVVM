package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zsh.mvvm.model.User;

/**
 * 作者：created by zsh19 on 2024/5/13 20:24
 * 邮箱：zsh1980794141@126.com
 */
public class LoginViewModel extends ViewModel {
    // public String account;
    // public String pwd;
    // public MutableLiveData<String> account = new MutableLiveData<>();
    // public MutableLiveData<String> pwd = new MutableLiveData<>();
    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }
}
