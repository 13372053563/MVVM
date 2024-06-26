package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zsh.mvvm.model.User;
import com.zsh.mvvm.repository.UserRepository;

/**
 * 作者：created by zsh19 on 2024/5/13 20:24
 * 邮箱：zsh1980794141@126.com
 */
public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }


    public MutableLiveData<com.zsh.mvvm.db.bean.User> localUser;

    public void getLocalUser() {
        UserRepository userRepository = new UserRepository();
        localUser = userRepository.getUser();
        failed = userRepository.failed;
    }
}
