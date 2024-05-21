package com.zsh.mvvm.ui.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ActivityHomeBinding;
import com.zsh.mvvm.utils.Constant;
import com.zsh.mvvm.utils.MVUtils;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        // 获取navController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.news_fragment:
                    binding.tvTitle.setText("头条新闻");
                    navController.navigate(R.id.news_fragment);
                    break;
                case R.id.video_fragment:
                    binding.tvTitle.setText("热门视频");
                    navController.navigate(R.id.video_fragment);
                    break;
                default:
            }
            return true;
        });
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_setting:
                    break;
                case R.id.item_logout:
                    logout();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        showMsg("退出登录");
        MVUtils.put(Constant.IS_LOGIN, false);
        jumpActivityFinish(LoginActivity.class);
    }
}
