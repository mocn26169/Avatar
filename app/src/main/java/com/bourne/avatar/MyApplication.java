package com.bourne.avatar;

import android.app.Application;

import com.bourne.avatar.GalleryFinal.GlideImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
https://github.com/pengjianbo/GalleryFinal

 主题的配置
 1)、GalleryFinal默认主题为DEFAULT（深蓝色）,还自带主题：DARK（黑色主题）、CYAN（蓝绿主题）、ORANGE（橙色主题）、GREEN（绿色主题）和TEAL（青绿色主题），当然也支持自定义主题（Custom Theme）,在自定义主题中用户可以配置字体颜色、图标颜色、更换图标、和背景色

 设置主题
 1)、使用自定义主题

 GalleryTheme theme = new GalleryTheme.Builder()
 .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
 .setTitleBarTextColor(Color.BLACK)
 .setTitleBarIconColor(Color.BLACK)
 .setFabNornalColor(Color.RED)
 .setFabPressedColor(Color.BLUE)
 .setCheckNornalColor(Color.WHITE)
 .setCheckSelectedColor(Color.BLACK)
 .setIconBack(R.mipmap.ic_action_previous_item)
 .setIconRotate(R.mipmap.ic_action_repeat)
 .setIconCrop(R.mipmap.ic_action_crop)
 .setIconCamera(R.mipmap.ic_action_camera)
 //...其他配置
 .build();
 GalleryFinal.init(theme);
 2)、ThemeConfig类说明

 setTitleBarTextColor//标题栏文本字体颜色
 setTitleBarBgColor//标题栏背景颜色
 setTitleBarIconColor//标题栏icon颜色，如果设置了标题栏icon，设置setTitleBarIconColor将无效
 setCheckNornalColor//选择框未选颜色
 setCheckSelectedColor//选择框选中颜色
 setCropControlColor//设置裁剪控制点和裁剪框颜色
 setFabNornalColor//设置Floating按钮Nornal状态颜色
 setFabPressedColor//设置Floating按钮Pressed状态颜色

 setIconBack//设置返回按钮icon
 setIconCamera//设置相机icon
 setIconCrop//设置裁剪icon
 setIconRotate//设置旋转icon
 setIconClear//设置清楚选择按钮icon（标题栏清除选择按钮）
 setIconFolderArrow//设置标题栏文件夹下拉arrow图标
 setIconDelete//设置多选编辑页删除按钮icon
 setIconCheck//设置checkbox和文件夹已选icon
 setIconFab//设置Floating按钮icon
 setEditPhotoBgTexture//设置图片编辑页面图片margin外背景
 setIconPreview设置预览按钮icon
 setPreviewBg设置预览页背景

 CoreConfig配置类
 Builder(Context context, ImageLoader imageLoader, ThemeConfig themeConfig) //构建CoreConfig所需ImageLoader和ThemeConfig
 setDebug //debug开关
 setEditPhotoCacheFolder(File file)//配置编辑（裁剪和旋转）功能产生的cache文件保存目录，不做配置的话默认保存在/sdcard/GalleryFinal/edittemp/
 setTakePhotoFolder设置拍照保存目录，默认是/sdcard/DICM/GalleryFinal/
 setFunctionConfig //配置全局GalleryFinal功能
 setNoAnimcation//关闭动画
 setPauseOnScrollListener//设置imageloader滑动加载图片优化OnScrollListener,根据选择的ImageLoader来选择PauseOnScrollListener
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(false)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

//        setMutiSelect(boolean)//配置是否多选
//        setMutiSelectMaxSize(int maxSize)//配置多选数量
//        setEnableEdit(boolean)//开启编辑功能
//        setEnableCrop(boolean)//开启裁剪功能
//        setEnableRotate(boolean)//开启旋转功能
//        setEnableCamera(boolean)//开启相机功能
//        setCropWidth(int width)//裁剪宽度
//        setCropHeight(int height)//裁剪高度
//        setCropSquare(boolean)//裁剪正方形
//        setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
//        setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
//        takePhotoFolter(File file)//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
//        setRotateReplaceSource(boolean)//配置选择图片时是否替换原始图片，默认不替换
//        setCropReplaceSource(boolean)//配置裁剪图片时是否替换原始图片，默认不替换
//        setForceCrop(boolean)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
//        setForceCropEdit(boolean)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
//        setEnablePreview(boolean)//是否开启预览功能


        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();

        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
//                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
                .build();

        GalleryFinal.init(coreConfig);
    }
}
