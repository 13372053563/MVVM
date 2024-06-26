package com.zsh.mvvm.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.api.ApiService;
import com.zsh.mvvm.db.bean.News;
import com.zsh.mvvm.model.NewsResponse;
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
 * 作者：created by zsh19 on 2024/5/19 15:52
 * 邮箱：zsh1980794141@126.com
 */
@SuppressLint("CheckResult")
public class NewsRepository {

    private static final String TAG = NewsRepository.class.getSimpleName();
    final MutableLiveData<NewsResponse> news = new MutableLiveData<>();
    public final MutableLiveData<String> failed = new MutableLiveData<>();

    /**
     * 获取新闻数据
     *
     * @return news
     */
    public MutableLiveData<NewsResponse> getNews() {
        // 今日此接口是否已经请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_NEWS)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_NEWS)) {
                getNewsForLocalDB();
            } else {
                getNewsForNetwork();
            }
        } else {
            getNewsForNetwork();
        }
        return news;
    }

    /**
     * 从本地数据库获取新闻
     */
    private void getNewsForLocalDB() {
        Log.d(TAG, "getNewsForLocalDB: 从本地数据库获取 新闻数据");
        NewsResponse newsResponse = new NewsResponse();
        NewsResponse.ResultBean resultBean = new NewsResponse.ResultBean();

        List<NewsResponse.ResultBean.DataBean> dataBeanList = new ArrayList<>();
        Flowable<List<News>> listFlowable = BaseApplication.getDb().newsDao().getAll();
        CustomDisposable.addDisposable(listFlowable, newss -> {
            for (News news1 : newss) {
                NewsResponse.ResultBean.DataBean dataBean = new NewsResponse.ResultBean.DataBean();
                dataBean.setUniquekey(news1.getUniquekey());
                dataBean.setTitle(news1.getTitle());
                dataBean.setDate(news1.getDate());
                dataBean.setAuthor_name(news1.getAuthor_name());
                dataBean.setCategory(news1.getCategory());
                dataBean.setThumbnail_pic_s(news1.getThumbnail_pic_s());
                dataBean.setIs_content(news1.getIs_content());
                dataBeanList.add(dataBean);
            }
            resultBean.setData(dataBeanList);
            newsResponse.setResult(resultBean);
            news.postValue(newsResponse);
        });
    }

    /**
     * 从网络获取壁纸数据
     */
    private void getNewsForNetwork() {
        Log.d(TAG, "getNewsForNetwork: 从网络获取 热门壁纸");
        NetworkApi.createService(ApiService.class, 2).
                news().compose(NetworkApi.applySchedulers(new DisposableObserver<NewsResponse>() {
                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        if (newsResponse.getError_code() == 0) {
                            // 保存本地数据
                            saveNews(newsResponse);
                            news.setValue(newsResponse);
                        } else {
                            failed.postValue(newsResponse.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        failed.postValue("News Error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    /**
     * 保存热门壁纸数据
     */
    private void saveNews(NewsResponse newsResponse) {
        MVUtils.put(Constant.IS_TODAY_REQUEST_NEWS, true);
        MVUtils.put(Constant.REQUEST_TIMESTAMP_NEWS, DateUtil.getMillisNextEarlyMorning());

        Completable deleteAll = BaseApplication.getDb().newsDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
            Log.d(TAG, "saveNews: 删除数据成功");
            List<News> newsList = new ArrayList<>();
            for (NewsResponse.ResultBean.DataBean dataBean : newsResponse.getResult().getData()) {
                newsList.add(new News(dataBean.getUniquekey(), dataBean.getTitle(), dataBean.getDate(), dataBean.getCategory(),
                        dataBean.getAuthor_name(), dataBean.getUrl(), dataBean.getThumbnail_pic_s(), dataBean.getIs_content()));
            }
            // 保存到数据库
            Completable insertAll = BaseApplication.getDb().newsDao().insertAll(newsList);
            Log.d(TAG, "saveNews: 插入数据：" + newsList.size() + "条");
            // RxJava处理Room数据存储
            CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "saveNews: 新闻数据保存成功"));
        });
    }
}
