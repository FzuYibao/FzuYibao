package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Maple27 on 2017/11/8.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {


        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);

    }

}
