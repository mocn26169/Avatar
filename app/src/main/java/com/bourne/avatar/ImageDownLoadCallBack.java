package com.bourne.avatar;

import android.graphics.Bitmap;
import java.io.File;

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file,String url);
    void onDownLoadSuccess(Bitmap bitmap,String url);

    void onDownLoadFailed();
}
