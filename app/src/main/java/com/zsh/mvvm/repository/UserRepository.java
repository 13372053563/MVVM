package com.zsh.mvvm.repository;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.db.bean.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;

/**
 * 作者：created by zsh19 on 2024/5/21 14:03
 * 邮箱：zsh1980794141@126.com
 */
public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

    private static volatile UserRepository mInstance;

    public static UserRepository getInstance() {
        if (mInstance == null) {
            synchronized (UserRepository.class) {
                if (mInstance == null) {
                    mInstance = new UserRepository();
                }
            }
        }
        return mInstance;
    }


    public MutableLiveData<User> getUser() {
        Flowable<List<User>> listFlowable = BaseApplication.getDb().userDao().getAll();
        CustomDisposable.addDisposable(listFlowable, users -> {
            if (!users.isEmpty()) {
                for (User user : users) {
                    if (user.getUid() == 1) {
                        userMutableLiveData.postValue(user);
                        break;
                    }
                }
            } else {
                failed.postValue("你还没有注册吧，去注册吧");
            }
        });
        return userMutableLiveData;
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUser(User user) {
        Completable completable = BaseApplication.getDb().userDao().update(user);
        CustomDisposable.addDisposable(completable, new Action() {
            @Override
            public void run() throws Exception {
                failed.postValue("200");
            }
        });
    }

    public void saveUser(User user) {
        Completable deleteAll = BaseApplication.getDb().userDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
            // 保存到数据库
            Completable insertAll = BaseApplication.getDb().userDao().insert(user);
            // RxJava处理Room数据存储
            CustomDisposable.addDisposable(insertAll, () -> {
                failed.postValue("200");
            });
        });
    }
}
