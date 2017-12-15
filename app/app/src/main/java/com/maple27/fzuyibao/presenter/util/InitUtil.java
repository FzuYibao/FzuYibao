package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.adapter.CommodityAdapter;
import com.maple27.fzuyibao.presenter.adapter.LibraryAdapter;
import com.maple27.fzuyibao.presenter.adapter.MainAdapter;
import com.maple27.fzuyibao.presenter.adapter.SeekAdapter;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.view.activity.DonateActivity;
import com.maple27.fzuyibao.view.activity.LMActivity;
import com.maple27.fzuyibao.view.activity.LibraryActivity;
import com.maple27.fzuyibao.view.activity.MarketActivity;
import com.maple27.fzuyibao.view.activity.PostNeedActivity;
import com.maple27.fzuyibao.view.activity.SeekActivity;
import com.maple27.fzuyibao.view.fragment.CommodityFragment;
import com.maple27.fzuyibao.view.fragment.HomeFragment;
import com.maple27.fzuyibao.view.fragment.MessageFragment;
import com.maple27.fzuyibao.view.fragment.PersonalFragment;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple27 on 2017/11/1.
 */


public class InitUtil {

    private static final String[] TAB_NAME = {"主页","消息","个人"};
    private static final String[] LM_TAB_NAME = {"专业课资料","考研资料","课外资料"};
    private static final String[] MARKET_TAB_NAME = {"电动车","锐捷","生活用品"};
    private static final int[] TAB_ICON_1 = {R.drawable.home1,R.drawable.message1,R.drawable.personal1};
    private static final int[] TAB_ICON_2 = {R.drawable.home2,R.drawable.message2,R.drawable.personal2};

    //初始化主Activity
    public static void initMainActivity(ViewPager viewPager, final TabLayout tabLayout,
                                        MainAdapter adapter, final List<Fragment> list){
        ////初始化主页视图
        HomeFragment fragment1 = new HomeFragment();
        MessageFragment fragment2 = new MessageFragment();
        PersonalFragment fragment3 = new PersonalFragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<list.size();i++){
            tabLayout.getTabAt(i).setText(TAB_NAME[i]);
            tabLayout.getTabAt(i).setIcon(TAB_ICON_1[i]);
        }

        //监听处理
        tabLayout.getTabAt(0).setIcon(TAB_ICON_2[0]);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(TAB_ICON_2[tab.getPosition()]);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(TAB_ICON_1[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setText(TAB_NAME[tab.getPosition()]);
            }
        });
    }

    //初始化HomeFragment
    public static void initHomeFragment(final Context context, final SmartRefreshLayout refresh, FloatingSearchView searchView,
                                        Banner banner, ImageView l_m, ImageView library, ImageView market, ImageView seek){

        //Refresh控件
        WaveSwipeHeader waveSwipeHeader = new WaveSwipeHeader(context);
        refresh.setRefreshHeader(waveSwipeHeader);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                //刷新请求
            }
        });

        //SearchBar控件
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                Toast.makeText(context, "去里面的板块搜"+currentQuery, Toast.LENGTH_SHORT).show();
                //搜索请求
            }
        });

        //Banner控件
        banner.setImageLoader(new GlideImageLoader());
        List<String> list = new ArrayList<>();
        //轮播图测试
        list.add("https://interface.fty-web.com/public/Carousel/Carousel1.jpg");
        list.add("https://interface.fty-web.com/public/Carousel/Carousel2.jpg");
        banner.setImages(list);
        banner.start();
        //跳转控件
        l_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LMActivity.class);
                context.startActivity(intent);
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LibraryActivity.class);
                context.startActivity(intent);
            }
        });
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MarketActivity.class);
                context.startActivity(intent);
            }
        });
        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeekActivity.class);
                context.startActivity(intent);
            }
        });

    }

    public static void initLMActivity(final Context context, ViewPager viewPager, final TabLayout tabLayout,
                                      CommodityAdapter adapter, final List<Fragment> list){
        ////初始化主页视图
        CommodityFragment fragment1 = CommodityFragment.newInstance("11000");
        CommodityFragment fragment2 = CommodityFragment.newInstance("12000");
        CommodityFragment fragment3 = CommodityFragment.newInstance("13000");
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<list.size();i++){
            tabLayout.getTabAt(i).setText(LM_TAB_NAME[i]);
        }
    }

    public static void initMarketActivity(final Context context, ViewPager viewPager, final TabLayout tabLayout,
                                          CommodityAdapter adapter, final List<Fragment> list){
        ////初始化主页视图
        CommodityFragment fragment1 = CommodityFragment.newInstance("31000");
        CommodityFragment fragment2 = CommodityFragment.newInstance("33000");
        CommodityFragment fragment3 = CommodityFragment.newInstance("32000");
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<list.size();i++){
            tabLayout.getTabAt(i).setText(MARKET_TAB_NAME[i]);
        }
    }

    public static void initLibraryActivity(final CustomApplication app, final Handler handler, final Context context, SmartRefreshLayout refresh, FloatingSearchView searchView,
                                           LibraryAdapter adapter, ListView listView, FloatingActionButton post){
        //Refresh控件
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                //刷新请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetLibraryInfo(handler);
                    }
                }).start();
            }
        });
        //SearchBar控件
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(final String currentQuery) {
                //搜索请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.SearchCommodity(handler,context,"20000",currentQuery,0);
                    }
                }).start();
            }
        });
        listView.setAdapter(adapter);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.setHandler(handler);
                Intent intent = new Intent(context, DonateActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public static void initSeekActivity(final CustomApplication app, final Handler handler, final Context context, SmartRefreshLayout refresh,
                                        SeekAdapter adapter, ListView listView, FloatingActionButton post){
        //Refresh控件
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                //刷新请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetSeekInfo(handler);
                    }
                }).start();
            }
        });
        listView.setAdapter(adapter);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.setHandler(handler);
                Intent intent = new Intent(context, PostNeedActivity.class);
                context.startActivity(intent);
            }
        });
    }



}
