package com.zsh.mvvm.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ItemForecastBinding;

import java.util.List;

/**
 * 作者：created by zsh19 on 2024/5/23 14:33
 * 邮箱：zsh1980794141@126.com
 */
public class ForecastAdapter extends BaseQuickAdapter<LocalDayWeatherForecast, BaseDataBindingHolder<ItemForecastBinding>> {
    public ForecastAdapter(@Nullable List<LocalDayWeatherForecast> data) {
        super(R.layout.item_forecast, data);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemForecastBinding> bindingHolder, LocalDayWeatherForecast localDayWeatherForecast) {
        ItemForecastBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setForecast(localDayWeatherForecast);
            binding.executePendingBindings();
        }
    }
}
