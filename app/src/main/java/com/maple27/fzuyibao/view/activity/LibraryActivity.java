package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.adapter.LibraryAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;

/**
 * Created by Maple27 on 2017/11/12.
 */

public class LibraryActivity extends AppCompatActivity {

    private Context context;
    private ListView lv;
    private LibraryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_library);
        init();
    }

    public void init(){
        context = this;
        lv = (ListView) findViewById(R.id.lv_LM);
        adapter = new LibraryAdapter(context);
        lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
