package com.zsh.mvvm.model;

import androidx.databinding.BaseObservable;

import java.util.List;

/**
 * 必应访问接口返回数据实体。https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1
 * 作者：created by zsh19 on 2024/5/13 20:15
 * 邮箱：zsh1980794141@126.com
 */

public class BiYingResponse {

    private TooltipsBean tooltips;
    private List<ImagesBean> images;

    public TooltipsBean getTooltips() {
        return tooltips;
    }

    public void setTooltips(TooltipsBean tooltips) {
        this.tooltips = tooltips;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class TooltipsBean {
        private String loading;

        @Override
        public String toString() {
            return "TooltipsBean{" +
                    "loading='" + loading + '\'' +
                    ", previous='" + previous + '\'' +
                    ", next='" + next + '\'' +
                    ", walle='" + walle + '\'' +
                    ", walls='" + walls + '\'' +
                    '}';
        }

        private String previous;
        private String next;
        private String walle;
        private String walls;

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getWalle() {
            return walle;
        }

        public void setWalle(String walle) {
            this.walle = walle;
        }

        public String getWalls() {
            return walls;
        }

        public void setWalls(String walls) {
            this.walls = walls;
        }
    }

    public static class ImagesBean extends BaseObservable {
        private String startdate;

        @Override
        public String toString() {
            return "ImagesBean{" +
                    "startdate='" + startdate + '\'' +
                    ", fullstartdate='" + fullstartdate + '\'' +
                    ", enddate='" + enddate + '\'' +
                    ", url='" + url + '\'' +
                    ", urlbase='" + urlbase + '\'' +
                    ", copyright='" + copyright + '\'' +
                    ", copyrightlink='" + copyrightlink + '\'' +
                    ", title='" + title + '\'' +
                    ", quiz='" + quiz + '\'' +
                    ", wp=" + wp +
                    ", hsh='" + hsh + '\'' +
                    ", drk=" + drk +
                    ", top=" + top +
                    ", bot=" + bot +
                    ", hs=" + hs +
                    '}';
        }

        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String title;
        private String quiz;
        private boolean wp;
        private String hsh;
        private int drk;
        private int top;
        private int bot;
        private List<?> hs;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
            notifyChange();
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public boolean isWp() {
            return wp;
        }

        public void setWp(boolean wp) {
            this.wp = wp;
        }

        public String getHsh() {
            return hsh;
        }

        public void setHsh(String hsh) {
            this.hsh = hsh;
        }

        public int getDrk() {
            return drk;
        }

        public void setDrk(int drk) {
            this.drk = drk;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBot() {
            return bot;
        }

        public void setBot(int bot) {
            this.bot = bot;
        }

        public List<?> getHs() {
            return hs;
        }

        public void setHs(List<?> hs) {
            this.hs = hs;
        }
    }


    @Override
    public String toString() {
        return "BiYingResponse{" +
                "tooltips=" + tooltips +
                ", images=" + images +
                '}';
    }
}

