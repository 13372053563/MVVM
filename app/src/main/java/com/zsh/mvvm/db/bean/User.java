package com.zsh.mvvm.db.bean;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 作者：created by zsh19 on 2024/5/21 13:53
 * 邮箱：zsh1980794141@126.com
 */
@Entity(tableName = "user")
public class User extends BaseObservable {
    @PrimaryKey
    private int uid;
    private String account;
    private String pwd;
    @Ignore
    private String confirmPwd;
    private String nickname;
    private String introduction;

    public User() {
    }

    public User(int uid, String account, String pwd, String confirmPwd, String nickname, String introduction) {
        this.uid = uid;
        this.account = account;
        this.pwd = pwd;
        this.confirmPwd = confirmPwd;
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
