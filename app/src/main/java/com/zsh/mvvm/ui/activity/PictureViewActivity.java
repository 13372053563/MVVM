package com.zsh.mvvm.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.zsh.mvvm.R;
import com.zsh.mvvm.ui.adapter.ImageAdapter;
import com.zsh.mvvm.databinding.ActivityPictureViewBinding;
import com.zsh.mvvm.viewmodel.PictureViewModel;

/**
 * 点击，显示壁纸的Activity
 */
public class PictureViewActivity extends BaseActivity {

    private PictureViewModel viewModel;
    private ActivityPictureViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);

        // 获取DataBinding，绑定xml
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture_view);
        // 获取ViewModel，绑定viewModel
        viewModel = new ViewModelProvider(this).get(PictureViewModel.class);
        String img = getIntent().getStringExtra("img");
        // 获取热门壁纸数据，请求壁纸数据
        viewModel.getWallPaper();
        // 监听页面的回调
        viewModel.wallPaper.observe(this, wallPapers -> {
            // 在回调中设置适配器的数据
            binding.vp2.setAdapter(new ImageAdapter(wallPapers));
            // 上一个页面点击的位置，没有与当前页面的位置一致
            for (int i = 0; i < wallPapers.size(); i++) {
                if (img == null) {
                    return;
                }
                // 通过传递过来的url地址和查询到的url地址进行比对，得到具体的位置。
                // 然后显示这个vp2的当前位置的item
                if (wallPapers.get(i).getImg().equals(img)) {
                    binding.vp2.setCurrentItem(i, false);
                    break;
                }
            }
        });
        /*String img = getIntent().getStringExtra("img");
          if (img != null) {
            ImageView imageView = findViewById(R.id.image);
            Glide.with(this)
                    .load(img)
                    .into(imageView);
        } */
    }
}