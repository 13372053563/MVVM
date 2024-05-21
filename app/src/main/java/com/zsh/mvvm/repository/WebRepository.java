package com.zsh.mvvm.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.api.ApiService;
import com.zsh.mvvm.model.NewsDetailResponse;
import com.zsh.mvvm.network.NetworkApi;

import io.reactivex.observers.DisposableObserver;

/**
 * 作者：created by zsh19 on 2024/5/20 20:51
 * 邮箱：zsh1980794141@126.com
 */
public class WebRepository {
    final MutableLiveData<NewsDetailResponse> newsDetail = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public MutableLiveData<NewsDetailResponse> getNewsDetail(String uniqueKey) {
        NetworkApi.createService(ApiService.class, 2).newsDetail(uniqueKey)
                .compose(NetworkApi.applySchedulers(new DisposableObserver<NewsDetailResponse>() {
                    @Override
                    public void onNext(NewsDetailResponse newsDetailResponse) {
                        // setValue()只能在主线程中调用，postValue()可以在任何线程中调用。
                        // 参考：https://blog.csdn.net/catzifeng/article/details/103931517
                        if (newsDetailResponse.getError_code() == 0) {
                            newsDetail.setValue(newsDetailResponse);
                        } else {
                            failed.postValue(newsDetailResponse.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        failed.postValue("NewsDetail Error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return newsDetail;
    }
}
