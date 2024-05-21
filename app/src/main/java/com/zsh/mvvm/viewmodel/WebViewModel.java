package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.LiveData;

import com.zsh.mvvm.model.NewsDetailResponse;
import com.zsh.mvvm.repository.WebRepository;

/**
 * 作者：created by zsh19 on 2024/5/20 20:57
 * 邮箱：zsh1980794141@126.com
 */
public class WebViewModel extends BaseViewModel {
    public LiveData<NewsDetailResponse> newsDetail;

    public void getNewsDetail(String uniqueKey) {
        WebRepository webRepository = new WebRepository();
        failed = webRepository.failed;
        newsDetail = webRepository.getNewsDetail(uniqueKey);
    }
}
