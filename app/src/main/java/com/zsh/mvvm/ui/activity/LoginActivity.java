package com.zsh.mvvm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ActivityLoginBinding;
import com.zsh.mvvm.model.User;
import com.zsh.mvvm.utils.Constant;
import com.zsh.mvvm.utils.MVUtils;
import com.zsh.mvvm.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity {
    private User user;
    ActivityLoginBinding dataBinding;
    LoginViewModel loginViewModel;
    private long timeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 双向绑定  数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel();
        // model --> view
        User user = new User("", "");
        loginViewModel.getUser().setValue(user);
        // 获取观察对象
        MutableLiveData<User> user1 = loginViewModel.getUser();
        user1.observe(this, user2 -> {
            Log.d("LoginActivity", "on Create: " + user2.getAccount());
            dataBinding.setViewModel(loginViewModel);
        });
        // 绑定单击事件
        dataBinding.btnLogin.setOnClickListener(v -> {
            if (loginViewModel.user.getValue().getAccount().isEmpty()) {
                showMsg("请输入账号");
                return;
            }
            if (loginViewModel.user.getValue().getPwd().isEmpty()) {
                showMsg("请输入密码");
                return;
            }
            // 检查输入的账户和密码是否是注册过的。
            checkUser();
        });
    }

    private void checkUser() {
        loginViewModel.getLocalUser();
        loginViewModel.localUser.observe(this, localUser -> {
            if (!loginViewModel.user.getValue().getAccount().equals(localUser.getAccount()) ||
                    !loginViewModel.user.getValue().getPwd().equals(localUser.getPwd())) {
                showMsg("账号或密码错误");
                return;
            }
            // 记录已经登陆过
            MVUtils.put(Constant.IS_LOGIN, true);
            showMsg("登录成功");
            jumpActivity(MainActivity.class);
        });
        loginViewModel.failed.observe(this, this::showMsg);
    }


    /**
     * Add a prompt to exit the application
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                showMsg("再次按下退出应用程序");
                timeMillis = System.currentTimeMillis();
            } else {
                exitTheProgram();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void toRegister(View view) {
        jumpActivity(RegisterActivity.class);
    }
}