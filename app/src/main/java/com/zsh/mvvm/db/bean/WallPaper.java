package com.zsh.mvvm.db.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 作者：created by zsh19 on 2024/5/15 19:40
 * 邮箱：zsh1980794141@126.com
 */
@Entity(tableName = "wallpaper")
public class WallPaper {

    @PrimaryKey(autoGenerate = true)
    private int uid = 0;

    private String img;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public WallPaper() {
    }

    @Ignore
    public WallPaper(String img) {
        this.img = img;
    }
}

