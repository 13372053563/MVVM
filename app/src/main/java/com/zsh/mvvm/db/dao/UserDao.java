package com.zsh.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zsh.mvvm.db.bean.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 作者：created by zsh19 on 2024/5/21 13:57
 * 邮箱：zsh1980794141@126.com
 */
@Dao
public interface UserDao {
    @Query("select * from user")
    Flowable<List<User>> getAll();

    @Update
    Completable update(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    @Query("DELETE from user")
    Completable deleteAll();
}
