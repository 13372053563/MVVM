package com.zsh.mvvm.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ActivityRegisterBinding;
import com.zsh.mvvm.db.bean.User;
import com.zsh.mvvm.viewmodel.RegisterViewModel;

/**
 * 作者：created by zsh19 on 2024/5/21 14:40
 * 邮箱：zsh1980794141@126.com
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUser().setValue(new User(0, "", "", "", null, null));
        binding.setRegister(registerViewModel);
        initView();
    }

    private void initView() {
        back(binding.toolbar);
        binding.btnRegister.setOnClickListener(v -> {
            if (registerViewModel.user.getValue().getAccount().isEmpty()) {
                showMsg("请输入账号");
                return;
            }
            if (registerViewModel.user.getValue().getPwd().isEmpty()) {
                showMsg("请输入密码");
                return;
            }
            if (registerViewModel.user.getValue().getConfirmPwd().isEmpty()) {
                showMsg("请确认密码");
                return;
            }
            if (!registerViewModel.user.getValue().getPwd().equals(registerViewModel.user.getValue().getConfirmPwd())) {
                showMsg("两次输入密码不一致");
                return;
            }

            registerViewModel.register();
            registerViewModel.failed.observe(this, failed -> {
                showMsg("200".equals(failed) ? "注册成功" : failed);
                if ("200".equals(failed)) {
                    finish();
                }
            });
        });
    }
}
