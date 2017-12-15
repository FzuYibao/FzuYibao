package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

import java.util.List;

/**
 * Created by lenovo-pc on 2017/12/12.
 */

public class CollectAdapter extends BaseAdapter{
    private Context context;
    private Handler handler;
    private LayoutInflater inflater = null;
    private CommodityBean bean;
    private GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";

    public CollectAdapter(Context context, CommodityBean bean, Handler handler){
        this.context = context;
        this.handler = handler;
        this.inflater = LayoutInflater.from(context);
        this.bean = bean;
        imageLoader = new GlideImageLoader();
    }

    public void setBean(CommodityBean bean){
        this.bean = bean;
    }

    @Override
    public int getCount() {
        if(bean.getData().getGoods()!=null){
            return bean.getData().getGoods().size();
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
        final int p =i;
        View newView;
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_collect, null);
            viewHolder.image = (ImageView) newView.findViewById(R.id.collect_image);
            viewHolder.collect = (ImageView) newView.findViewById(R.id.collect_collect);
            viewHolder.name = (TextView) newView.findViewById(R.id.collect_name);
            viewHolder.price = (TextView) newView.findViewById(R.id.collect_price);
            viewHolder.userName = (TextView) newView.findViewById(R.id.collect_username);
            viewHolder.avatar = (CircleImageView) newView.findViewById(R.id.collect_avatar);
            newView.setTag(viewHolder);
        }else{
            newView = view;
            viewHolder = (ViewHolder) newView.getTag();
        }
        if (bean.getData().getGoods().get(i).getPhoto()!=null){
            imageLoader.displayImage(context, MAINURL+bean.getData().getGoods().get(i).getPhoto().get(0),viewHolder.image);
        }
        imageLoader.displayImage(context, MAINURL+bean.getData().getGoods().get(i).getAvatar_path(),viewHolder.avatar);
        viewHolder.name.setText(bean.getData().getGoods().get(i).getGoods_name());
        viewHolder.price.setText("¥"+bean.getData().getGoods().get(i).getPrice());
        viewHolder.userName.setText(bean.getData().getGoods().get(i).getNickname());
        viewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.DeleteCollect(handler,bean.getData().getGoods().get(p).getGid());
                    }
                }).start();
            }
        });
        return newView;
    }

    static class ViewHolder{
        public ImageView image;
        public ImageView collect;
        public CircleImageView avatar;
        public TextView name;
        public TextView price;
        public TextView userName;

    }
}
