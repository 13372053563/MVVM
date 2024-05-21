package com.zsh.mvvm.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ActivitySplashBinding;
import com.zsh.mvvm.utils.Constant;
import com.zsh.mvvm.utils.EasyAnimation;
import com.zsh.mvvm.utils.MVUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_splash);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setStatusBar(true);
        EasyAnimation.moveViewWidth(binding.tvTranslate, () -> {
            binding.tvMvvm.setVisibility(View.VISIBLE);
            jumpActivity(MVUtils.getBoolean(Constant.IS_LOGIN) ? MainActivity.class : LoginActivity.class);
        });
    }
}