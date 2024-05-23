package com.zsh.mvvm.model;

import com.amap.api.services.weather.LocalWeatherLive;

/**
 * 作者：created by zsh19 on 2024/5/23 14:15
 * 邮箱：zsh1980794141@126.com
 */
public class LiveWeather {
    private String district;
    private LocalWeatherLive localWeatherLive;

    public LiveWeather(String district, LocalWeatherLive localWeatherLive) {
        this.district = district;
        this.localWeatherLive = localWeatherLive;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LocalWeatherLive getLocalWeatherLive() {
        return localWeatherLive;
    }

    public void setLocalWeatherLive(LocalWeatherLive localWeatherLive) {
        this.localWeatherLive = localWeatherLive;
    }
}
