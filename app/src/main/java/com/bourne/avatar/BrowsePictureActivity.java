package com.bourne.avatar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.bourne.avatar.ActionSheetDialog.OnSheetItemClickListener;
import com.bourne.avatar.ActionSheetDialog.SheetItemColor;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.lang.ref.WeakReference;

public class BrowsePictureActivity extends AppCompatActivity {

    private String url;
    private PhotoView view;
    private static final int MSG_VISIBLE = 0x001;
    private static final int MSG_ERROR = 0x002;
    private DownLoadHandler downLoadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_picture);
        downLoadHandler = new DownLoadHandler(this);

////        在需要隐藏虚拟键Navigation Bar的Activity的onCreate方法中添加如下代码：
//        Window _window = getWindow();
//        WindowManager.LayoutParams params = _window.getAttributes();
////        始终隐藏，触摸屏幕时也不出现
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
////        隐藏了，但触摸屏幕时出现
////        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        _window.setAttributes(params);

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

        if (!TextUtils.isEmpty(url)) {
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
                                            onDownLoad(url);
                                        }
                                    })

                            .show();
                    return true;
                }
            });
        }
    }

    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        ProgressUtils.show(BrowsePictureActivity.this, null, "正在保存，请耐心等候......");
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file, String url) {

                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap, String url) {
                        // 在这里执行图片保存方法
                        Message message = new Message();
                        message.what = MSG_VISIBLE;
                        message.obj = url;
                        downLoadHandler.sendMessage(message);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        Message message = new Message();
                        message.what = MSG_ERROR;
                        downLoadHandler.sendMessage(message);
                    }
                });

        //启动图片下载线程
        new Thread(service).start();
    }

    private static class DownLoadHandler extends Handler {

        private WeakReference<BrowsePictureActivity> activityWeakReference;

        public DownLoadHandler(BrowsePictureActivity activity) {
            this.activityWeakReference = new WeakReference<BrowsePictureActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            ProgressUtils.dissmiss();

            BrowsePictureActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == MSG_VISIBLE) {
                    new AlertDialog(activity).builder()
                            .setMsg("图片保存成功，路径为" + msg.obj)
                            .setNegativeButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();

//                    Toast.makeText(activity, "文件已保存在" + msg.obj, Toast.LENGTH_LONG).show();
                } else if (msg.what == MSG_ERROR) {
                    new AlertDialog(activity).builder()
                            .setMsg("图片保存失败" + msg.obj)
                            .setNegativeButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
//                    Toast.makeText(activity, "文件保存失败", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downLoadHandler.removeCallbacksAndMessages(null);
    }
}
