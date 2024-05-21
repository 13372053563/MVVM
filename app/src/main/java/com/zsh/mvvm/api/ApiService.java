package com.zsh.mvvm.api;

import com.zsh.mvvm.model.BiYingResponse;
import com.zsh.mvvm.model.NewsDetailResponse;
import com.zsh.mvvm.model.NewsResponse;
import com.zsh.mvvm.model.VideoResponse;
import com.zsh.mvvm.model.WallPaperResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：created by zsh19 on 2024/5/13 20:16
 * 邮箱：zsh1980794141@126.com
 */
public interface ApiService {
    /**
     * 必应每日一图
     */
    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1")
    Observable<BiYingResponse> biying();

    /**
     * 热门壁纸
     */
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Observable<WallPaperResponse> wallPaper();

    /**
     * 聚合新闻数据
     */
    @GET("/toutiao/index?type=&page=&page_size=&is_filter=&key=9a094d3e5a69aa56d1646fcfe058ccaf")
    Observable<NewsResponse> news();

    /**
     * 聚合热门视频数据
     */
    @GET("/fapig/douyin/billboard?type=hot_video&size=20&key=c02855c213f6659f6ef0a4b67d9a3ac9")
    Observable<VideoResponse> video();

    /**
     * 返回新闻详情数据
     *
     * @param uniquekey 通过新闻数据可以获取uniquekey
     */
    @GET("/toutiao/content?key=9a094d3e5a69aa56d1646fcfe058ccaf")
    Observable<NewsDetailResponse> newsDetail(@Query("uniquekey") String uniquekey);
}
