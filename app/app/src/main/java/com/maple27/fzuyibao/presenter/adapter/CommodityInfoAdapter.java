package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by Maple27 on 2017/11/18.
 */

public class CommodityInfoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater = null;
    private CommodityBean bean;
    private GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";

    public CommodityInfoAdapter(Context context, CommodityBean bean){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bean = bean;
        imageLoader = new GlideImageLoader();
    }

    public void setBean(CommodityBean bean){
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().getGoods().size();
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
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_commodity, null);
            viewHolder.image = (ImageView) newView.findViewById(R.id.commodity_image);
            viewHolder.name = (TextView) newView.findViewById(R.id.commodity_name);
            viewHolder.info = (TextView) newView.findViewById(R.id.commodity_info);
            viewHolder.price = (TextView) newView.findViewById(R.id.commodity_price);
            viewHolder.userName = (TextView) newView.findViewById(R.id.commodity_username);
            viewHolder.avatar = (CircleImageView) newView.findViewById(R.id.commodity_avatar);
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
        viewHolder.info.setText(bean.getData().getGoods().get(i).getDescription());
        viewHolder.price.setText("¥"+bean.getData().getGoods().get(i).getPrice());
        viewHolder.userName.setText(bean.getData().getGoods().get(i).getNickname());
        return newView;
    }

    static class ViewHolder{
        public ImageView image;
        public CircleImageView avatar;
        public TextView name;
        public TextView info;
        public TextView price;
        public TextView userName;

    }
}
