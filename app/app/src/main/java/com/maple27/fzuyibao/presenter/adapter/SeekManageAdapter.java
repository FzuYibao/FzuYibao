package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.SeekBean;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by Maple27 on 2017/12/15.
 */

public class SeekManageAdapter extends BaseAdapter{

    private Context context;
    private Handler handler;
    private LayoutInflater inflater = null;
    private SeekBean bean;
    private GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";

    public SeekManageAdapter(Context context, SeekBean bean, Handler handler){
        this.context = context;
        this.handler = handler;
        this.inflater = LayoutInflater.from(context);
        this.bean = bean;
        imageLoader = new GlideImageLoader();
    }

    public void setBean(SeekBean bean){
        this.bean = bean;
    }

    @Override
    public int getCount() {
        if(bean.getData().getWants()!=null){
            return bean.getData().getWants().size();
        }else return 0;

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
        final ViewHolder viewHolder;
        View newView;
        final int p = i;
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_seekmanage, null);
            viewHolder.username = (TextView) newView.findViewById(R.id.sm_userName);
            viewHolder.info = (TextView) newView.findViewById(R.id.sm_info);
            viewHolder.time = (TextView) newView.findViewById(R.id.sm_time);
            viewHolder.delete = (ImageView) newView.findViewById(R.id.sm_delete);
            viewHolder.avatar = (CircleImageView) newView.findViewById(R.id.sm_avatar);
            newView.setTag(viewHolder);
        }else{
            newView = view;
            viewHolder = (ViewHolder) newView.getTag();
        }
        if (bean.getData().getWants().get(i).getAvatar_path()!=null){
            imageLoader.displayImage(context, MAINURL+bean.getData().getWants().get(i).getAvatar_path(),viewHolder.avatar);
        }
        viewHolder.username.setText(bean.getData().getWants().get(i).getNickname());
        viewHolder.info.setText(bean.getData().getWants().get(i).getDescription());
        viewHolder.time.setText(bean.getData().getWants().get(i).getTime());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.DeleteSeek(handler, bean.getData().getWants().get(p).getWid());
                    }
                }).start();
            }
        });
        return newView;
    }

    static class ViewHolder{
        public CircleImageView avatar;
        public TextView username;
        public TextView info;
        public TextView time;
        public ImageView delete;
    }

}
