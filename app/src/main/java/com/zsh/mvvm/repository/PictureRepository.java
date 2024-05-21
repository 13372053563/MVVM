package com.zsh.mvvm.repository;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.db.bean.WallPaper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 页面有数据了之后，都应该创建一个对应页面的Repository
 * 作者：created by zsh19 on 2024/5/16 09:01
 * 邮箱：zsh1980794141@126.com
 */
public class PictureRepository {
    private final MutableLiveData<List<WallPaper>> wallPaper = new MutableLiveData<>();

    public MutableLiveData<List<WallPaper>> getWallPaper() {
        Flowable<List<WallPaper>> flowable = BaseApplication.getDb().wallPaperDao().getAll();
        CustomDisposable.addDisposable(flowable, new Consumer<List<WallPaper>>() {
            @Override
            public void accept(List<WallPaper> wallPapers) throws Exception {
                wallPaper.postValue(wallPapers);
            }
        });
        return wallPaper;
    }
}
