package com.zsh.mvvm.viewmodel;

import androidx.lifecycle.LiveData;

import com.zsh.mvvm.model.VideoResponse;
import com.zsh.mvvm.repository.VideoRepository;

public class VideoViewModel extends BaseViewModel {

    public LiveData<VideoResponse> video;

    public void getVideo() {
        VideoRepository videoRepository = new VideoRepository();
        failed = videoRepository.failed;
        video = videoRepository.getVideo();
    }
}
