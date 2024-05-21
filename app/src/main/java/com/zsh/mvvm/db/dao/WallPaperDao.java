package com.zsh.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zsh.mvvm.db.bean.WallPaper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 作者：created by zsh19 on 2024/5/15 19:40
 * 邮箱：zsh1980794141@126.com
 */
@Dao
public interface WallPaperDao {

    @Query("SELECT * FROM wallpaper")
    Flowable<List<WallPaper>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<WallPaper> wallPapers);

    @Query("DELETE FROM wallpaper")
    Completable deleteAll();
}

