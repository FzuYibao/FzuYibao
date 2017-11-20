package com.maple27.fzuyibao.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.view.activity.MainActivity;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;
import com.yanzhenjie.album.Album;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class PersonalFragment extends Fragment {

    private Activity activity;
    private Context context;
    private CircleImageView avatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal , null);
        init(view);
        return view;
    }

    public void init(View view){
        activity = this.getActivity();
        context = getContext();
        avatar = (CircleImageView) view.findViewById(R.id.avatar);
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.displayImage(context, UserEntity.getAvatar_path(), avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.startAlbum(activity, 100 , 1);
            }
        });
    }

}
