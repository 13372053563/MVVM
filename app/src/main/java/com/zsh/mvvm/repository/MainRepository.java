package com.zsh.mvvm.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.api.ApiService;
import com.zsh.mvvm.db.bean.Image;
import com.zsh.mvvm.db.bean.WallPaper;
import com.zsh.mvvm.model.BiYingResponse;
import com.zsh.mvvm.model.WallPaperResponse;
import com.zsh.mvvm.network.NetworkApi;
import com.zsh.mvvm.network.utils.DateUtil;
import com.zsh.mvvm.network.utils.KLog;
import com.zsh.mvvm.utils.Constant;
import com.zsh.mvvm.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * Main存储库 用于对数据进行处理
 * 作者：created by zsh19 on 2024/5/13 20:17
 * 邮箱：zsh1980794141@126.com
 */


public class MainRepository {
    private static final String TAG = MainRepository.class.getSimpleName();
    final MutableLiveData<BiYingResponse> biyingImage = new MutableLiveData<>();
    /**
     * 热门壁纸数据
     */
    final MutableLiveData<WallPaperResponse> wallPaper = new MutableLiveData<>();


    /**
     * 获取壁纸数据
     *
     * @return wallPaper
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<WallPaperResponse> getWallPaper() {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_WALLPAPER)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_WALLPAPER)) {
                getLocalDbForWallPaper();
            } else {
                requestNetworkApiForWallPaper();
            }
        } else {
            requestNetworkApiForWallPaper();
        }
        return wallPaper;
    }

    /**
     * 获取必应的每日一图数据
     *
     * @return biyingImage
     */
    public MutableLiveData<BiYingResponse> getBiYing() {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP)) {
                // 数据依然有效
                getLocalDB();
            } else {
                // 数据失效了，重新获取
                requestNetworkApi();
            }
        } else {
            requestNetworkApi();
        }
        return biyingImage;
    }

    @SuppressLint("CheckResult")
    private void requestNetworkApi() {
        KLog.d(TAG, "requestNetworkApi: 从网络获取必应每日一图");
        ApiService apiService = NetworkApi.createService(ApiService.class, 0);
        apiService.biying().compose(NetworkApi.applySchedulers(new DisposableObserver<BiYingResponse>() {
            @Override
            public void onNext(BiYingResponse biYingImgResponse) {
                // 存储到本地数据库中，并记录今日已请求了数据
                saveImageData(biYingImgResponse);
                biyingImage.setValue(biYingImgResponse);
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("BiYing Error: " + e.toString());
            }

            @Override
            public void onComplete() {

            }
        }));
    }


    /**
     * 保存图片到数据库
     *
     * @param biYingResponse
     */
    private void saveImageData(BiYingResponse biYingResponse) {
        // 记录今日已经请求
        MVUtils.put(Constant.IS_TODAY_REQUEST, true);
        // 记录此次请求的最晚有效时间。第二天0点
        MVUtils.put(Constant.REQUEST_TIMESTAMP, DateUtil.getMillisNextEarlyMorning());
        BiYingResponse.ImagesBean bean = biYingResponse.getImages().get(0);
        // 保存到数据库
        Completable completable = BaseApplication.getDb().imageDao().insertAll(
                new Image(1, bean.getUrl(), bean.getUrlbase(), bean.getCopyright(), bean.getCopyrightlink(), bean.getTitle())
        );
        // RxJava处理Room数据存储
        CustomDisposable.addDisposable(completable, new Action() {
            @Override
            public void run() throws Exception {
                KLog.d(TAG, "saveImageData：保存数据成功");
            }
        });
    }

    /**
     * 从本地数据库获取
     */
    private void getLocalDB() {
        KLog.d(TAG, "getLocalDB: 从本地数据库获取必应每日一图");
        BiYingResponse biYingImgResponse = new BiYingResponse();
        // 从数据库获取
        Flowable<Image> imageFlowable = BaseApplication.getDb().imageDao().queryById(1);
        // RxJava处理Room数据获取
        CustomDisposable.addDisposable(imageFlowable, new Consumer<Image>() {
            @Override
            public void accept(Image image) throws Exception {
                BiYingResponse.ImagesBean imagesBean = new BiYingResponse.ImagesBean();
                imagesBean.setUrl(image.getUrl());
                imagesBean.setUrlbase(image.getUrlbase());
                imagesBean.setCopyright(image.getCopyright());
                imagesBean.setCopyrightlink(image.getCopyrightlink());
                imagesBean.setTitle(image.getTitle());
                List<BiYingResponse.ImagesBean> imagesBeanList = new ArrayList<>();
                imagesBeanList.add(imagesBean);
                biYingImgResponse.setImages(imagesBeanList);
                biyingImage.postValue(biYingImgResponse);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void requestNetworkApiForWallPaper() {
        KLog.d(TAG, "从网络中请求热门壁纸数据");
        NetworkApi.createService(ApiService.class, 1).wallPaper()
                .compose(NetworkApi.applySchedulers(new DisposableObserver<WallPaperResponse>() {
                    @Override
                    public void onNext(WallPaperResponse wallPaperResponse) {
                        saveWallPaper(wallPaperResponse);
                        wallPaper.setValue(wallPaperResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("WallPaper Error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    /**
     * 保存热门壁纸到数据库
     *
     * @param wallPaperResponse
     */
    private void saveWallPaper(WallPaperResponse wallPaperResponse) {
        MVUtils.put(Constant.IS_TODAY_REQUEST_WALLPAPER, true);
        MVUtils.put(Constant.REQUEST_TIMESTAMP_WALLPAPER, DateUtil.getMillisNextEarlyMorning());

        Completable deleteAll = BaseApplication.getDb().wallPaperDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, new Action() {
            @Override
            public void run() throws Exception {
                KLog.d(TAG, "saveWallPaper：删除数据库数据成功");
                // 构建新的插入数据库的数据
                ArrayList<WallPaper> wallPapers = new ArrayList<>();
                for (WallPaperResponse.ResBean.VerticalBean verticalBean : wallPaperResponse.getRes().getVertical()) {
                    wallPapers.add(new WallPaper(verticalBean.getImg()));
                }
                // 数据插入到数据库
                Completable insertAll = BaseApplication.getDb().wallPaperDao().insertAll(wallPapers);
                KLog.d(TAG, "saveWallPaper：保存了" + wallPapers.size() + "条数据");
                // RxJava处理Room数据存储
                CustomDisposable.addDisposable(insertAll, new Action() {
                    @Override
                    public void run() throws Exception {
                        KLog.d(TAG, "saveWallPaper：热门壁纸数据保存成功");
                    }
                });
            }
        });
    }

    private void getLocalDbForWallPaper() {
        KLog.d(TAG, "getLocalDbForWallPaper：从本地数据库获取 热门壁纸");
        WallPaperResponse wallPaperResponse = new WallPaperResponse();
        WallPaperResponse.ResBean resBean = new WallPaperResponse.ResBean();
        List<WallPaperResponse.ResBean.VerticalBean> verticalBeans = new ArrayList<>();
        // 从数据库中查询到数据
        Flowable<List<WallPaper>> listFlowable = BaseApplication.getDb().wallPaperDao().getAll();
        // RxJava对查询到的数据进行操作
        CustomDisposable.addDisposable(listFlowable, new Consumer<List<WallPaper>>() {
            @Override
            public void accept(List<WallPaper> wallPapers) throws Exception {
                for (WallPaper paper : wallPapers) {
                    WallPaperResponse.ResBean.VerticalBean bean = new WallPaperResponse.ResBean.VerticalBean();
                    bean.setImg(paper.getImg());
                    verticalBeans.add(bean);
                }
                // 组装数据，用于显示
                resBean.setVertical(verticalBeans);
                wallPaperResponse.setRes(resBean);
                // 显示在ui中
                wallPaper.postValue(wallPaperResponse);
            }
        });
    }
}
