package com.zsh.mvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.zsh.mvvm.db.bean.User;
import com.zsh.mvvm.repository.UserRepository;

/**
 * 作者：created by zsh19 on 2024/5/21 14:33
 * 邮箱：zsh1980794141@126.com
 */
public class RegisterViewModel extends BaseViewModel {
    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public void register() {
        UserRepository userRepository = new UserRepository();
        failed = userRepository.failed;
        user.getValue().setUid(1);
        Log.d("TAG", "register: " + new Gson().toJson(user.getValue()));
        userRepository.saveUser(user.getValue());
    }
}
