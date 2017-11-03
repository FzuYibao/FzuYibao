package com.maple27.fzuyibao.presenter.util;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.adapter.MainAdapter;
import com.maple27.fzuyibao.view.fragment.HomeFragment;
import com.maple27.fzuyibao.view.fragment.MessageFragment;
import com.maple27.fzuyibao.view.fragment.PersonalFragment;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/1.
 */


public class InitMainActivityUtil {

    private static final String[] TAB_NAME = {"主页","消息","个人"};
    private static final int[] TAB_ICON_1 = {R.drawable.album_abc_spinner_white,R.drawable.album_ic_add_photo_white,R.drawable.album_ic_done_white};
    private static final int[] TAB_ICON_2 = {R.drawable.album_abc_spinner_white,R.drawable.album_ic_add_photo_white,R.drawable.album_ic_done_white};

    public static void init(ViewPager viewPager, final TabLayout tabLayout, MainAdapter adapter, final List<Fragment> list){
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
                for(int i=0;i<list.size();i++){
                    tabLayout.getTabAt(i).setIcon(TAB_ICON_1[i]);
                }
                tab.setIcon(TAB_ICON_2[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
