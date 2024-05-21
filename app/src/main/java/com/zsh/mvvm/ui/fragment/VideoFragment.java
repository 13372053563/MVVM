package com.zsh.mvvm.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.VideoFragmentBinding;
import com.zsh.mvvm.ui.adapter.VideoAdapter;
import com.zsh.mvvm.viewmodel.VideoViewModel;

public class VideoFragment extends BaseFragment {

    private VideoFragmentBinding binding;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.video_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VideoViewModel mViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        // 获取视频数据
        mViewModel.getVideo();
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        // 数据刷新
        mViewModel.video.observe(context, videoResponse ->
                binding.rv.setAdapter(new VideoAdapter(videoResponse.getResult())));
        mViewModel.failed.observe(context, this::showMsg);
    }

}
