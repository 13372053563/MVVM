package com.zsh.mvvm.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理Dao操作的返回值
 * 作者：created by zsh19 on 2024/5/15 09:36
 * 邮箱：zsh1980794141@126.com
 */
public class CustomDisposable {
    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Flowable  背压模式
     *
     * @param flowable
     * @param consumer
     * @param <T>
     */
    public static <T> void addDisposable(Flowable<T> flowable, Consumer<T> consumer) {
        compositeDisposable.add(flowable
                .subscribeOn(Schedulers.io()) // 改变调用它之前代码的线程
                .observeOn(AndroidSchedulers.mainThread()) // 改变调用它之后代码的线程
                .subscribe(consumer));
    }

    /**
     * Completable
     *
     * @param completable
     * @param action
     * @param <T>
     */
    public static <T> void addDisposable(Completable completable, Action action) {
        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action));
    }
}
