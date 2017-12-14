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
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by Maple27 on 2017/12/13.
 */

public class CommodityManageAdapter extends BaseAdapter {

    private Context context;
    private Handler handler;
    private LayoutInflater inflater = null;
    private CommodityBean bean;
    private GlideImageLoader imageLoader;
    public static String MAINURL = "https://interface.fty-web.com/";

    public CommodityManageAdapter(Context context, CommodityBean bean, Handler handler){
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
        View newView;
        final int p = i;
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_commoditymanage, null);
            viewHolder.image = (ImageView) newView.findViewById(R.id.cm_image);
            viewHolder.name = (TextView) newView.findViewById(R.id.cm_name);
            viewHolder.info = (TextView) newView.findViewById(R.id.cm_info);
            viewHolder.price = (TextView) newView.findViewById(R.id.cm_price);
            viewHolder.off = (Button) newView.findViewById(R.id.cm_off);
            newView.setTag(viewHolder);
        }else{
            newView = view;
            viewHolder = (ViewHolder) newView.getTag();
        }
        if (bean.getData().getGoods().get(i).getPhoto()!=null){
            imageLoader.displayImage(context, MAINURL+bean.getData().getGoods().get(i).getPhoto().get(0),viewHolder.image);
        }
        viewHolder.name.setText(bean.getData().getGoods().get(i).getGoods_name());
        viewHolder.info.setText(bean.getData().getGoods().get(i).getDescription());
        viewHolder.price.setText("¥"+bean.getData().getGoods().get(i).getPrice());
        viewHolder.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.OffCommodity(handler, bean.getData().getGoods().get(p).getGid());
                    }
                }).start();
            }
        });
        return newView;
    }

    static class ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView info;
        public TextView price;
        public Button off;
    }
}

