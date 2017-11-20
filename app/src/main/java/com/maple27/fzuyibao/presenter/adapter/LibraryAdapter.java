package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.maple27.fzuyibao.R;

/**
 * Created by Maple27 on 2017/11/16.
 */

public class LibraryAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;

    public LibraryAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView;
        newView = inflater.inflate(R.layout.listitem_library, null);
        return newView;
    }

}
