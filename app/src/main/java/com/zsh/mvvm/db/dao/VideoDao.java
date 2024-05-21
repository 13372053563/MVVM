package com.zsh.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zsh.mvvm.db.bean.Video;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 作者：created by zsh19 on 2024/5/19 15:49
 * 邮箱：zsh1980794141@126.com
 */
@Dao
public interface VideoDao {

    @Query("SELECT * FROM video")
    Flowable<List<Video>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Video> videos);

    @Query("DELETE FROM video")
    Completable deleteAll();
}

