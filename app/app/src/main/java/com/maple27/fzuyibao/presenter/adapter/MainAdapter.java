package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public MainAdapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
