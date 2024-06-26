package com.zsh.mvvm.ui.fragment;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.DialogWeatherBinding;
import com.zsh.mvvm.databinding.FragmentMapBinding;
import com.zsh.mvvm.model.LiveWeather;
import com.zsh.mvvm.ui.adapter.ForecastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MapFragment extends BaseFragment implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener, WeatherSearch.OnWeatherSearchListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private List<LocalDayWeatherForecast> weatherForecast;
    // 解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;
    private GeocodeSearch geocoderSearch = null;
    private String district = null;// 区/县
    private FragmentMapBinding binding;
    private LocalWeatherLive liveResult;

    public static MapFragment newsInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.updatePrivacyShow(requireActivity(), true, true);
        MapsInitializer.updatePrivacyAgree(requireActivity(), true);
        binding.mapView.onCreate(savedInstanceState);
        // 点击按钮显示天气弹窗
        binding.fabWeather.setOnClickListener(v -> showWeatherDialog());

        initMap();
        initSearch();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        // 初始化地图控制器对象
        AMap aMap = binding.mapView.getMap();
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        MyLocationStyle style = new MyLocationStyle();// 初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);// 定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(style);// 设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 修改放大缩小按钮的位置
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        // 设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 初始化搜索
     */
    private void initSearch() {
        try {
            geocoderSearch = new GeocodeSearch(requireActivity());
            geocoderSearch.setOnGeocodeSearchListener(this);
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    private void showWeatherDialog() {
        // 隐藏浮动按钮
        binding.fabWeather.hide();
        BottomSheetDialog dialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogStyle_Light);
        DialogWeatherBinding weatherBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.dialog_weather, null, false);
        // 设置数据源
        weatherBinding.setLiveWeather(new LiveWeather(district, liveResult));
        // 配置天气预报列表
        ForecastAdapter forecastAdapter = new ForecastAdapter(weatherForecast);
        weatherBinding.rvForecast.setLayoutManager(new LinearLayoutManager(requireActivity()));
        weatherBinding.rvForecast.setAdapter(forecastAdapter);

        dialog.setContentView(weatherBinding.getRoot());
        dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        // 弹窗关闭时显示浮动按钮
        dialog.setOnDismissListener(v -> binding.fabWeather.show());
        dialog.show();
    }

    /**
     * 搜索天气
     *
     * @param type WEATHER_TYPE_LIVE 实时天气   WEATHER_TYPE_FORECAST  预报天气
     */
    private void searchWeather(int type) {
        WeatherSearchQuery weatherSearchQuery = new WeatherSearchQuery(district, type);
        try {
            WeatherSearch weatherSearch = new WeatherSearch(requireActivity());
            weatherSearch.setOnWeatherSearchListener(this);
            weatherSearch.setQuery(weatherSearchQuery);
            weatherSearch.searchWeatherAsyn(); // 异步搜索
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if (location != null) {
            Log.e(TAG, "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            // 创建一个经纬度点，参数一是维度，参数二是经度
            LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
            // 第一个参数表示Latlng，第二参数表示范围多少米，第三个参数表示火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
            // 通过经纬度获取地址信息
            geocoderSearch.getFromLocationAsyn(query);
        } else {
            Log.e(TAG, "定位失败");
        }
    }

    /**
     * 坐标转地址
     *
     * @param regeocodeResult
     * @param rCode
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        // 解析result获取地址描述信息
        if (rCode == PARSE_SUCCESS_CODE) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            // 显示解析后的地址
            Log.e(TAG, "地址：" + regeocodeAddress.getFormatAddress());
            district = regeocodeAddress.getDistrict();
            Log.e(TAG, "区：" + district);
            // 搜索天气，实时天气和预报天气
            searchWeather(WeatherSearchQuery.WEATHER_TYPE_LIVE);
            searchWeather(WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        } else {
            showMsg("获取地址失败");
        }
    }

    /**
     * 地址转坐标
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 实时天气返回
     *
     * @param localWeatherLiveResult
     * @param code
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int code) {
        liveResult = localWeatherLiveResult.getLiveResult();
        if (liveResult != null) {
            Log.e(TAG, "onWeatherLiveSearched: " + new Gson().toJson(liveResult));
        } else {
            showMsg("实时天气数据为空");
        }
    }

    /**
     * 天气预报返回
     *
     * @param localWeatherForecastResult
     * @param code
     */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int code) {
        weatherForecast = localWeatherForecastResult.getForecastResult().getWeatherForecast();
        if (weatherForecast != null) {
            Log.e(TAG, "onWeatherForecastSearched: " + new Gson().toJson(weatherForecast));
            binding.fabWeather.show();
        } else {
            showMsg("天气预报数据为空");
        }
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }
}