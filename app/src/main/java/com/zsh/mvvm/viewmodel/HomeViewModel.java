package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.LiveData;

import com.zsh.mvvm.db.bean.User;
import com.zsh.mvvm.repository.UserRepository;

/**
 * 作者：created by zsh19 on 2024/5/22 09:39
 * 邮箱：zsh1980794141@126.com
 */
public class HomeViewModel extends BaseViewModel {
    public LiveData<User> user;

    public String defaultName = "初学者-study";
    public String defaultIntroduction = "Android | Java";

    public void getUser() {
        user = UserRepository.getInstance().getUser();
    }

    public void updateUser(User user) {
        UserRepository.getInstance().updateUser(user);
        failed = UserRepository.getInstance().failed;
        getUser();
    }
}
