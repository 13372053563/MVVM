package com.zsh.mvvm.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zsh.mvvm.db.bean.WallPaper;
import com.zsh.mvvm.repository.PictureRepository;

import java.util.List;

/**
 * 作者：created by zsh19 on 2024/5/16 09:04
 * 邮箱：zsh1980794141@126.com
 */
public class PictureViewModel extends ViewModel {
    public LiveData<List<WallPaper>> wallPaper;

    public void getWallPaper() {
        wallPaper = new PictureRepository().getWallPaper();
    }
}
