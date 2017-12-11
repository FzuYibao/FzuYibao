package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.SeekBean;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class SeekAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater = null;
    private SeekBean bean;
    private GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";

    public SeekAdapter(Context context, SeekBean bean){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bean = bean;
        imageLoader = new GlideImageLoader();
    }

    @Override
    public int getCount() {
        if(bean.getData()!=null){
            return bean.getData().getWants().size();
        }else{
            return 0;
        }
    }

    public void setBean(SeekBean bean){
        this.bean = bean;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        View newView;
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_seek, null);
            viewHolder.info = (TextView) newView.findViewById(R.id.seek_info);
            viewHolder.userName = (TextView) newView.findViewById(R.id.seek_userName);
            viewHolder.avatar = (CircleImageView) newView.findViewById(R.id.seek_avatar);
            viewHolder.time = (TextView) newView.findViewById(R.id.seek_time);
            viewHolder.button = (Button) newView.findViewById(R.id.seek_connect);
            newView.setTag(viewHolder);
        }else{
            newView = view;
            viewHolder = (ViewHolder) newView.getTag();
        }
        imageLoader.displayImage(context, MAINURL+bean.getData().getWants().get(i).getAvatar_path(),viewHolder.avatar);
        viewHolder.info.setText(bean.getData().getWants().get(i).getDescription());
        viewHolder.time.setText(bean.getData().getWants().get(i).getTime());
        viewHolder.userName.setText(bean.getData().getWants().get(i).getNickname());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtil.startMessageEntry(context, bean.getData().getWants().get(i).getSno());
            }
        });
        return newView;
    }

    static class ViewHolder{
        public CircleImageView avatar;
        public TextView userName;
        public TextView info;
        public TextView time;
        public Button button;
    }
}
