package com.zsh.mvvm.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ItemNewsBinding;
import com.zsh.mvvm.model.NewsResponse;
import com.zsh.mvvm.ui.activity.WebActivity;

import java.util.List;

/**
 * 作者：created by zsh19 on 2024/5/20 09:32
 * 邮箱：zsh1980794141@126.com
 */
public class NewsAdapter extends BaseQuickAdapter<NewsResponse.ResultBean.DataBean, BaseDataBindingHolder<ItemNewsBinding>> {
    public NewsAdapter(@Nullable List<NewsResponse.ResultBean.DataBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemNewsBinding> bindingHolder, NewsResponse.ResultBean.DataBean dataBean) {
        ItemNewsBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setNews(dataBean);
            binding.setOnClick(new ClickBinding());
            binding.executePendingBindings();
        }
    }

    public static class ClickBinding {
        public void itemClick(NewsResponse.ResultBean.DataBean dataBean, View view) {
            if ("1".equals(dataBean.getIs_content())) {
                Intent intent = new Intent(view.getContext(), WebActivity.class);
                intent.putExtra("uniqueKey", dataBean.getUniquekey());
                view.getContext().startActivity(intent);
            } else {
                Toast.makeText(view.getContext(), "没有详情信息", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
