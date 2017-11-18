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

import java.util.List;

/**
 * Created by IMTB on 2017/11/16.
 */

public class MessageChooseAdapter extends BaseAdapter {

    List<MessageChooseViewEntity> mData = null;
    LayoutInflater mInflater;

    public MessageChooseAdapter(Context context, List<MessageChooseViewEntity> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
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
        MessageChooseAdapter.ViewHolder viewHolder = null;
        MessageChooseViewEntity data = mData.get(i);
        if (view == null) {
            viewHolder = new MessageChooseAdapter.ViewHolder();
            view = mInflater.inflate(R.layout.listitem_message_choose, null);
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.content = (TextView) view.findViewById(R.id.content);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.reminder = (FrameLayout) view.findViewById(R.id.message_reminder);
            viewHolder.new_num = (TextView) view.findViewById(R.id.message_reminder_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MessageChooseAdapter.ViewHolder) view.getTag();
        }

//        viewHolder.avatar
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
