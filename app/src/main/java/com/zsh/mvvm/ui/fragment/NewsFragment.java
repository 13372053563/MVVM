package com.zsh.mvvm.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsh.mvvm.databinding.NewsFragmentBinding;
import com.zsh.mvvm.ui.adapter.NewsAdapter;
import com.zsh.mvvm.viewmodel.NewsViewModel;
import com.zsh.mvvm.R;

public class NewsFragment extends BaseFragment {
    private NewsFragmentBinding binding;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsViewModel mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        // 获取新闻数据
        mViewModel.getNews();
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        // 数据刷新
        mViewModel.news.observe(context, newsResponse -> {
            binding.rv.setAdapter(new NewsAdapter(newsResponse.getResult().getData()));
        });
        mViewModel.failed.observe(context, this::showMsg);
    }
}