package com.zsh.mvvm.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.api.ApiService;
import com.zsh.mvvm.db.bean.Video;
import com.zsh.mvvm.model.VideoResponse;
import com.zsh.mvvm.network.BaseObserver;
import com.zsh.mvvm.network.NetworkApi;
import com.zsh.mvvm.network.utils.DateUtil;
import com.zsh.mvvm.utils.Constant;
import com.zsh.mvvm.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

/**
 * 作者：created by zsh19 on 2024/5/20 08:59
 * 邮箱：zsh1980794141@126.com
 */
@SuppressLint("CheckResult")
public class VideoRepository {

    public static final String TAG = VideoRepository.class.getSimpleName();

    final MutableLiveData<VideoResponse> video = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    /**
     * 获取视频数据
     *
     * @return video
     */
    public MutableLiveData<VideoResponse> getVideo() {
        // 今日此接口是否已经请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_VIDEO)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_VIDEO)) {
                getVideoForLocalDB();
            } else {
                getVideoForNetwork();
            }
        } else {
            getVideoForNetwork();
        }
        return video;
    }

    /**
     * 从本地数据库获取新闻
     */
    private void getVideoForLocalDB() {
        Log.d(TAG, "getVideoForLocalDB: 从本地数据库获取 视频数据");
        VideoResponse videoResponse = new VideoResponse();

        List<VideoResponse.ResultBean> dataBeanList = new ArrayList<>();
        Flowable<List<Video>> listFlowable = BaseApplication.getDb().videoDao().getAll();
        CustomDisposable.addDisposable(listFlowable, videos -> {
            for (Video video : videos) {
                VideoResponse.ResultBean resultBean = new VideoResponse.ResultBean();
                resultBean.setTitle(video.getTitle());
                resultBean.setShare_url(video.getShare_url());
                resultBean.setAuthor(video.getAuthor());
                resultBean.setHot_words(video.getHot_words());
                resultBean.setItem_cover(video.getItem_cover());
                dataBeanList.add(resultBean);
            }
            videoResponse.setResult(dataBeanList);
            video.postValue(videoResponse);
        });
    }

    /**
     * 从网络获取壁纸数据
     */
    private void getVideoForNetwork() {
        Log.d(TAG, "getVideoForNetwork: 从网络获取 热门壁纸");
        NetworkApi.createService(ApiService.class, 3)
                .video().compose(NetworkApi.applySchedulers(new DisposableObserver<VideoResponse>() {
                    @Override
                    public void onNext(VideoResponse videoResponse) {
                        if (videoResponse.getError_code() == 0) {
                            // 保存本地数据
                            saveVideo(videoResponse);
                            video.postValue(videoResponse);
                        } else {
                            failed.postValue(videoResponse.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        failed.postValue("Video Error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    /**
     * 保存热门壁纸数据
     */
    private void saveVideo(VideoResponse videoResponse) {
        MVUtils.put(Constant.IS_TODAY_REQUEST_VIDEO, true);
        MVUtils.put(Constant.REQUEST_TIMESTAMP_VIDEO, DateUtil.getMillisNextEarlyMorning());

        Completable deleteAll = BaseApplication.getDb().videoDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
            Log.d(TAG, "saveVideo: 删除数据成功");
            List<Video> videoList = new ArrayList<>();
            for (VideoResponse.ResultBean resultBean : videoResponse.getResult()) {
                videoList.add(new Video(resultBean.getTitle(), resultBean.getShare_url(), resultBean.getAuthor(),
                        resultBean.getItem_cover(), resultBean.getHot_words()));
            }
            // 保存到数据库
            Completable insertAll = BaseApplication.getDb().videoDao().insertAll(videoList);
            Log.d(TAG, "saveVideo: 插入数据：" + videoList.size() + "条");
            // RxJava处理Room数据存储
            CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveVideo: 视频数据保存成功"));
        });
    }
}


