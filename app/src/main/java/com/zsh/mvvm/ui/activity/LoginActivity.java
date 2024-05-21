package com.zsh.mvvm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

    // private static final String TAG = MainActivity.class.getSimpleName();
    // private TextInputEditText etAccount, etPwd;
    // private TextView tvAccount, tvPwd;

    private User user;
    ActivityLoginBinding dataBinding;
    LoginViewModel loginViewModel;
    private long timeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // setContentView(R.layout.activity_main);
        /* Log.e(TAG, "进入 onCreate ...");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);

        tvAccount = findViewById(R.id.tv_account);
        tvPwd = findViewById(R.id.tv_pwd);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.account.setValue(etAccount.getText().toString().trim());
                mainViewModel.pwd.setValue(etPwd.getText().toString().trim());
                if (TextUtils.isEmpty(mainViewModel.account.getValue())) {
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(mainViewModel.pwd.getValue())) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
        mainViewModel.account.observe(this, account -> tvAccount.setText("账号：" + account));
        mainViewModel.pwd.observe(this, pwd -> tvPwd.setText("密码：" + pwd)); */

        // 单向绑定
        /* ActivityMainBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        user = new User("admin", "123456");
        // 不能忘记绑定，不然看不到效果
        dataBinding.setUser(user);
        dataBinding.btnLogin.setOnClickListener(v -> {
            // 手动更改数据源，将数据通知到xml
            Toast.makeText(this, "etAccount：" + dataBinding.etAccount.getText(), Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "etAccount：" + dataBinding.etPwd.getText(), Toast.LENGTH_SHORT).show();
            user.setAccount(dataBinding.etAccount.getText().toString().trim());
            user.setPwd(dataBinding.etPwd.getText().toString().trim());
        }); */

        // 双向绑定
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel();
        // model --> view
        User user = new User("admin", "123456");
        loginViewModel.getUser().setValue(user);
        // 获取观察对象
        MutableLiveData<User> user1 = loginViewModel.getUser();
        user1.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                dataBinding.setViewModel(loginViewModel);
            }
        });
        // 绑定单击事件
        dataBinding.btnLogin.setOnClickListener(v -> {
            if (loginViewModel.user.getValue().getAccount().isEmpty()) {
                showMsg("请输入账号");
                // Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loginViewModel.user.getValue().getPwd().isEmpty()) {
                showMsg("请输入密码");
                // Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            MVUtils.put(Constant.IS_LOGIN, true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
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
}