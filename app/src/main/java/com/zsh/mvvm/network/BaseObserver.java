package com.zsh.mvvm.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：created by zsh19 on 2024/5/13 20:05
 * 邮箱：zsh1980794141@126.com
 */
public abstract class BaseObserver<T> implements Observer<T> {
    // 开始
    @Override
    public void onSubscribe(Disposable d) {

    }

    // 继续
    @Override
    public void onNext(T t) {

    }

    // 异常
    @Override
    public void onError(Throwable e) {

    }

    // 完成
    @Override
    public void onComplete() {

    }

    // 成功
    public abstract void onSuccess(T t);

    // 失败
    public abstract void onFailure(Throwable e);
}
