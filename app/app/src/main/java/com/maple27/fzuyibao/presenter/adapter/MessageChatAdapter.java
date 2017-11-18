package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageChatViewEntity;

import java.util.List;

/**
 * Created by IMTB on 2017/11/17.
 */

public class MessageChatAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    List<MessageChatViewEntity> mData;

    public MessageChatAdapter(Context context, List<MessageChatViewEntity> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        MessageChatViewEntity m = mData.get(position);
        return m.getDirection();
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
        MessageChatViewEntity data = mData.get(i);
        if(view == null){
            viewHolder = new ViewHolder();
            switch (getItemViewType(i)){
                case MessageChatViewEntity.LEFT:
                    view = mInflater.inflate(R.layout.listview_message_chat_left, null);
                    break;
                case MessageChatViewEntity.RIGHT:
                    view = mInflater.inflate(R.layout.listview_message_chat_right, null);
                    break;
            }
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.content = (TextView) view.findViewById(R.id.content);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(data.getName());
        viewHolder.content.setText(data.getContent());
        return view;
    }

    class ViewHolder{
        ImageView avatar;
        TextView name;
        TextView content;
    }

}
