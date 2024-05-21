package com.zsh.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zsh.mvvm.db.bean.News;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 作者：created by zsh19 on 2024/5/19 15:49
 * 邮箱：zsh1980794141@126.com
 */
@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    Flowable<List<News>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<News> news);

    @Query("DELETE FROM news")
    Completable deleteAll();
}
