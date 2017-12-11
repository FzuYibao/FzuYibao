package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageChooseViewEntity;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;

import java.util.List;

/**
 * Created by IMTB on 2017/11/16.
 */

public class MessageChooseAdapter extends BaseAdapter {

    Context context;
    List<MessageChooseViewEntity> mData = null;
    LayoutInflater mInflater;
    GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";


    public MessageChooseAdapter(Context context, List<MessageChooseViewEntity> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        imageLoader = new GlideImageLoader();
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        MessageChooseViewEntity data = mData.get(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.listitem_message_choose, null);
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.content = (TextView) view.findViewById(R.id.content);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.reminder = (FrameLayout) view.findViewById(R.id.message_reminder);
            viewHolder.new_num = (TextView) view.findViewById(R.id.message_reminder_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //imageLoader.displayImage(context,MAINURL);
        viewHolder.title.setText(data.getTitle());
        viewHolder.content.setText(data.getContent());
        viewHolder.time.setText(data.getTime());
//        viewHolder.new_num.setText(data.getNewMessageNum());
//        viewHolder.reminder

        return view;
    }

    class ViewHolder {
        ImageView avatar;
        TextView title;
        TextView content;
        TextView time;
        FrameLayout reminder;
        TextView new_num;
    }
}
