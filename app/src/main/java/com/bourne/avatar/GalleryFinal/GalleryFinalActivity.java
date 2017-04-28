package com.bourne.avatar.GalleryFinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bourne.avatar.ActionSheetDialog;
import com.bourne.avatar.ActionSheetDialog.OnSheetItemClickListener;
import com.bourne.avatar.ActionSheetDialog.SheetItemColor;
import com.bourne.avatar.BrowsePictureActivity;
import com.bourne.avatar.R;
import com.bourne.avatar.SelectableRoundedImageView;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class GalleryFinalActivity extends Activity implements View.OnClickListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;
    private String url = "https://github.com/wasabeef/awesome-android-ui/raw/master/art/Android-MonthCalendarWidget.png";
    private SelectableRoundedImageView avatar;

    private FunctionConfig functionConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_final);
        initView();
    }

    private void initView() {
        avatar = (SelectableRoundedImageView) findViewById(R.id.avatar);
        avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        avatar.setOval(true);
        avatar.setOnClickListener(this);

//        Glide.with(this).load("https://github.com/square/leakcanary/raw/master/assets/icon_512.png")
//                .placeholder(R.mipmap.ic_launcher).crossFade().into(avatar);

        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(avatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                if (!TextUtils.isEmpty(url)) {
                    new ActionSheetDialog(GalleryFinalActivity.this)
                            .builder()
                            .setCancelable(true)
                            .setCanceledOnTouchOutside(true)
                            .addSheetItem("相册", SheetItemColor.Blue,
                                    new OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            // GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                                            //带配置
                                            functionConfig = new FunctionConfig.Builder()
                                                    .setEnableCamera(false)
                                                    .setEnableEdit(false)
                                                    .setEnableCrop(true)
                                                    .setEnableRotate(true)
                                                    .setCropSquare(true)
                                                    .setEnablePreview(true)
                                                    .build();
                                            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                        }
                                    })
                            .addSheetItem("拍照", SheetItemColor.Blue,
                                    new OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                                        }
                                    }
                            )
                            .addSheetItem("查看大图", SheetItemColor.Blue,
                                    new OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            Intent intent = new Intent(GalleryFinalActivity.this, BrowsePictureActivity.class);
                                            intent.putExtra("url", url);
                                            startActivity(intent);
                                        }
                                    }
                            )
                            .show();
                } else {
                    new ActionSheetDialog(GalleryFinalActivity.this)
                            .builder()
                            .setCancelable(true)
                            .setCanceledOnTouchOutside(true)
                            .addSheetItem("相册", SheetItemColor.Blue,
                                    new OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {

                                        }
                                    })
                            .addSheetItem("拍照", SheetItemColor.Blue,
                                    new OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                                        }
                                    }
                            )
                            .show();
                }

                break;

            default:
                break;
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null && resultList.size() > 0) {
                url = resultList.get(0).getPhotoPath();
                Log.e("MainActivity", "url=" + url);

                Glide
                        .with(GalleryFinalActivity.this)
                        .load("file://" + url)
                        .asBitmap()
//                        .load("https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=2561733620,2304282159&fm=85&s=E71450848ABEFCCE643AA8800300308C")
//                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
//                        .crossFade()
                        .into(avatar);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(GalleryFinalActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
