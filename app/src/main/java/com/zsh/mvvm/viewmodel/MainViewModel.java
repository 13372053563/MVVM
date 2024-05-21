package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zsh.mvvm.model.BiYingResponse;
import com.zsh.mvvm.model.WallPaperResponse;
import com.zsh.mvvm.repository.MainRepository;

public class MainViewModel extends ViewModel {

    // 页面中的值
    public LiveData<BiYingResponse> biying;

    public LiveData<WallPaperResponse> wallPaper;

    public void getBiying() {
        biying = new MainRepository().getBiYing();
    }

    public void getWallPaper() {
        wallPaper = new MainRepository().getWallPaper();
    }
}