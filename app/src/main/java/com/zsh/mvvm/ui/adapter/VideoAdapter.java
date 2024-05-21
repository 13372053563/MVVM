package com.zsh.mvvm.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ItemVideoBinding;
import com.zsh.mvvm.model.VideoResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 作者：created by zsh19 on 2024/5/20 09:35
 * 邮箱：zsh1980794141@126.com
 */
public class VideoAdapter extends BaseQuickAdapter<VideoResponse.ResultBean, BaseDataBindingHolder<ItemVideoBinding>> {

    public VideoAdapter(@Nullable List<VideoResponse.ResultBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemVideoBinding> bindingHolder, VideoResponse.ResultBean dataBean) {
        ItemVideoBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setVideo(dataBean);
            binding.setOnClick(new ClickBinding());
            binding.executePendingBindings();
        }
    }

    public static class ClickBinding {
        public void itemClick(@NotNull VideoResponse.ResultBean resultBean, View view) {
            if (resultBean.getShare_url() != null) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resultBean.getShare_url())));
            } else {
                Toast.makeText(view.getContext(), "视频地址为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

