package com.bourne.avatar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;


public class ProgressUtils {

    private static ProgressDialog dialog;

    public static void dissmiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static AlertDialog show(Context context, String title, String content) {

        if (dialog == null) {
            dialog = ProgressDialog.show(context, title, content, false, false);
        }
        return dialog;
    }

    public static AlertDialog showCircle(Context context, String title, String content) {

        if (dialog == null) {
            dialog = new ProgressDialog(context);
            //实例化
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //设置进度条风格，风格为圆形，旋转的
            dialog.setTitle(title);
            //设置ProgressDialog 标题
            dialog.setMessage(content);
//            设置ProgressDialog 提示信息
//            dialog.setIcon(R.drawable.android);
            //设置ProgressDialog 标题图标
//            dialog.setButton("Google",context);
            //设置ProgressDialog 的一个Button
//            dialog.setIndeterminate(false);
            //设置ProgressDialog 的进度条是否不明确
            dialog.setCancelable(false);
            //设置ProgressDialog 是否可以按退回按键取消
            dialog.show();
        }
        return dialog;
    }
}
