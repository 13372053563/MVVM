package com.zsh.mvvm.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.zsh.mvvm.BaseApplication;
import com.zsh.mvvm.R;
import com.zsh.mvvm.network.utils.KLog;

/**
 * 继承父类ShapeableImageView就可以让图片拥有这个属性shapeAppearanceOverlay
 * 这个属性值可以让你的Image图片做很多的形状上的变化，可以将图片的边框修改为圆角
 * 作者：created by zsh19 on 2024/5/13 20:35
 * 邮箱：zsh1980794141@126.com
 */
public class CustomImageView extends ShapeableImageView {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .placeholder(R.drawable.wallpaper_bg)// 图片加载出来前，显示的图片
            .fallback(R.drawable.wallpaper_bg) // url为空的时候,显示的图片
            .error(R.mipmap.ic_loading_failed);// 图片加载失败后，显示的图片

    private static final RequestOptions OPTIONS_LOCAL = new RequestOptions()
            .placeholder(R.drawable.logo)// 图片加载出来前，显示的图片
            .fallback(R.drawable.logo) // url为空的时候,显示的图片
            .error(R.mipmap.ic_loading_failed)// 图片加载失败后，显示的图片
            .diskCacheStrategy(DiskCacheStrategy.NONE)// 不做磁盘缓存
            .skipMemoryCache(true);

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 必应壁纸  因为拿到的url不完整，因此需要做一次地址拼接
     *
     * @param imageView 图片视图
     * @param url       网络url
     */
    @BindingAdapter(value = {"biyingUrl"}, requireAll = false)
    public static void setBiyingUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            url = "/th?id=OHR.NamibiaCanyon_ZH-CN3973338246_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";
        } else {
            Log.e("Zhang", "更新图片url");
        }
        String assembleUrl = "http://cn.bing.com" + url;
        KLog.d(assembleUrl);
        Glide.with(BaseApplication.getContext()).load(assembleUrl).apply(OPTIONS_LOCAL).into(imageView);
    }

    /**
     * 普通网络地址图片
     *
     * @param imageView 图片视图
     * @param url       网络url
     */
    @BindingAdapter(value = {"networkUrl"}, requireAll = false)
    public static void setNetworkUrl(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext()).load(url).apply(OPTIONS).into(imageView);
    }

    @BindingAdapter(value = {"localUrl"}, requireAll = false)
    public static void setLocalUrl(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext()).load(url).apply(OPTIONS_LOCAL).into(imageView);
    }
}
