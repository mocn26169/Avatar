package com.bourne.avatar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bourne.avatar.ActionSheetDialog.OnSheetItemClickListener;
import com.bourne.avatar.ActionSheetDialog.SheetItemColor;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class BrowsePictureActivity extends AppCompatActivity {

    private String url;
    private PhotoView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_picture);

//        在需要隐藏虚拟键Navigation Bar的Activity的onCreate方法中添加如下代码：
        Window _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
//        始终隐藏，触摸屏幕时也不出现
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
//        隐藏了，但触摸屏幕时出现
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        _window.setAttributes(params);

        url = getIntent().getStringExtra("url");
        view = (PhotoView) findViewById(R.id.iv_photo);

        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowsePictureActivity.this.finish();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new ActionSheetDialog(BrowsePictureActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("保存到手机", SheetItemColor.Blue,
                                new OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })

                        .show();
                return true;
            }
        });
    }
}
