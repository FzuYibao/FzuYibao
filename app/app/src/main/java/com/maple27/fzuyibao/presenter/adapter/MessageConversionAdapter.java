package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageConversionEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by IMTB on 2017/11/16.
 */

public class MessageConversionAdapter extends BaseSwipeAdapter {

    Context mContext;
    List<MessageConversionEntity> mData;



    public MessageConversionAdapter(Context context, List<MessageConversionEntity> data) {
        Log.i("ConversionAdapter", "init->init");
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        Log.i("ConversionAdapter", "generateView->init view");

        View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_conversion, null);
        SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
            }
        });
        view.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
        MessageConversionEntity messageConversionEntity = mData.get(position);
        Log.i("ConversionAdapter", "fillValues->init data: potions:" + position + "  user:" + messageConversionEntity.getReciver().getAccount());

        ImageView mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView mTitle = (TextView) convertView.findViewById(R.id.title);
        TextView mContent = (TextView) convertView.findViewById(R.id.content);
        TextView mTime = (TextView) convertView.findViewById(R.id.time);

        Log.i("ConversionAdapter", "fillValues->init avatar:" + messageConversionEntity.getAvatar());
        Glide.with(mContent).load(messageConversionEntity.getAvatar()).into(mAvatar);

        mContent.setText(messageConversionEntity.getContent());
        mTitle.setText(messageConversionEntity.getTitle());
        mTime.setText(messageConversionEntity.getTime());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
