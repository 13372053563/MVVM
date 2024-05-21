package com.zsh.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zsh.mvvm.db.bean.Image;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 作者：created by zsh19 on 2024/5/14 10:32
 * 邮箱：zsh1980794141@126.com
 */
@Dao
public interface ImageDao {
    @Query("SELECT * FROM image")
    List<Image> getAll();

    /**
     * 使用Flowable可以做到线程之间的切换
     * 由于读取速率可能 远大于 观察者处理速率，故使用背压 Flowable 模式。
     * 这是为了防止表中数据过多，读取速率远大于接收数据，从而导致内存溢出的问题
     * @param uid
     * @return
     */
    @Query("SELECT * FROM image WHERE uid LIKE :uid LIMIT 1")
    Flowable<Image> queryById(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(Image... images);

    @Delete
    void delete(Image image);
}
