package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.model.NewsResponse;
import com.zsh.mvvm.repository.NewsRepository;

public class NewsViewModel extends BaseViewModel {
    public MutableLiveData<NewsResponse> news;

    public void getNews() {
        NewsRepository newsRepository = new NewsRepository();
        failed = newsRepository.failed;
        news = newsRepository.getNews();
    }
}