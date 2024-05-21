package com.zsh.mvvm.ui.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ItemImageBinding;
import com.zsh.mvvm.db.bean.WallPaper;

import java.util.List;

/**
 * 继承BaseQuickAdapter，通过使用需要适配器中的实体Bean，
 * 然后是ViewHolder，这里使用的是BaseDataBindingHolder，最终是继承RecyclerView.ViewHolder，
 * 同时传递了ItemImageBinding，这是布局在编译时生成的。
 * 作者：created by zsh19 on 2024/5/16 08:27
 * 邮箱：zsh1980794141@126.com
 */
public class ImageAdapter extends BaseQuickAdapter<WallPaper, BaseDataBindingHolder<com.zsh.mvvm.databinding.ItemImageBinding>> {
    public ImageAdapter(@Nullable List<WallPaper> data) {
        super(R.layout.item_image, data);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemImageBinding> bindingHolder, WallPaper wallPaper) {
        if (wallPaper == null) {
            return;
        }
        // 获取布局中绑定的数据
        ItemImageBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            // 为布局中绑定的数据设置值
            binding.setWallPaper(wallPaper);
            binding.executePendingBindings();
        }
    }
}
