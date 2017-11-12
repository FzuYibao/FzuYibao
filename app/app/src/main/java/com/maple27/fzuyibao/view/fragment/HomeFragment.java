package com.maple27.fzuyibao.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class HomeFragment extends Fragment {

    private Context context;
    private SmartRefreshLayout refresh;
    private FloatingSearchView searchView;
    private Banner banner;
    private ImageView l_m;
    private ImageView library;
    private ImageView market;
    private ImageView seek;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home , null);
        init(view);
        return view;
    }

    public void init(View view){
        context=this.getContext();
        refresh = (SmartRefreshLayout) view.findViewById(R.id.refresh_home);
        searchView = (FloatingSearchView) view.findViewById(R.id.search_bar_home);
        banner = (Banner) view.findViewById(R.id.banner);
        l_m = (ImageView) view.findViewById(R.id.l_m);
        library = (ImageView) view.findViewById(R.id.library);
        market = (ImageView) view.findViewById(R.id.market);
        seek = (ImageView) view.findViewById(R.id.seek);
        InitUtil.initHomeFragment(context, refresh, searchView, banner, l_m, library, market, seek);
    }

}
