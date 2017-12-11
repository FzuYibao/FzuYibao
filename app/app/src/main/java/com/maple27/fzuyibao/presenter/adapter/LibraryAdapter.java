package com.maple27.fzuyibao.presenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.LibraryBean;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by Maple27 on 2017/11/16.
 */

public class LibraryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater = null;
    private LibraryBean bean;
    private GlideImageLoader imageLoader;
    private int p;
    public static String MAINURL = "https://interface.fty-web.com/";

    public LibraryAdapter(Context context, LibraryBean bean){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bean = bean;
        imageLoader = new GlideImageLoader();
    }

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 10){
                ViewHolder viewHolder = (ViewHolder) msg.obj;
                viewHolder.status.setText("已租借");
                viewHolder.status.setTextColor(context.getResources().getColor(R.color.colorRed));
                viewHolder.rent.setVisibility(View.INVISIBLE);
                viewHolder.times.setText(""+(Integer.valueOf(bean.getData().getBooks().get(p).getRent_times())+1));
                viewHolder.deadline.setText("2017-12-26");
                Toast.makeText(context, "租借成功，请联系书主", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void setBean(LibraryBean bean){
        this.bean = bean;
    }

    @Override
    public int getCount() {
        if(bean.getData()!=null){
            return bean.getData().getBooks().size();
        }else{
            return 0;
        }
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
        final int position = i;
        View newView;
        if(view == null){
            viewHolder = new ViewHolder();
            newView = inflater.inflate(R.layout.listitem_library, null);
            viewHolder.status = (TextView) newView.findViewById(R.id.book_status);
            viewHolder.image = (ImageView) newView.findViewById(R.id.book_image);
            viewHolder.name = (TextView) newView.findViewById(R.id.book_name);
            viewHolder.times = (TextView) newView.findViewById(R.id.book_rent_times);
            viewHolder.deadline = (TextView) newView.findViewById(R.id.book_deathLine);
            viewHolder.userName = (TextView) newView.findViewById(R.id.book_donator);
            viewHolder.avatar = (CircleImageView) newView.findViewById(R.id.book_donator_avatar);
            viewHolder.rent = (Button) newView.findViewById(R.id.book_rent);
            newView.setTag(viewHolder);
        }else{
            newView = view;
            viewHolder = (ViewHolder) newView.getTag();
        }
        imageLoader.displayImage(context, MAINURL+bean.getData().getBooks().get(i).getPhoto().get(0),viewHolder.image);
        imageLoader.displayImage(context, MAINURL+bean.getData().getBooks().get(i).getAvatar_path(),viewHolder.avatar);
        viewHolder.name.setText(bean.getData().getBooks().get(i).getBooks_name());
        viewHolder.times.setText(bean.getData().getBooks().get(i).getRent_times());
        if(viewHolder.status.equals("1")){
            viewHolder.deadline.setText("-");
        }else{
            viewHolder.deadline.setText("2017-12-26");
        }

        viewHolder.userName.setText(bean.getData().getBooks().get(i).getNickname());
        if(bean.getData().getBooks().get(i).getStatus().equals("1")){
            viewHolder.status.setText("可租借");
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.rent.setVisibility(View.VISIBLE);
        }else{
            viewHolder.status.setText("已租借");
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.rent.setVisibility(View.INVISIBLE);
        }
        viewHolder.rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.Rent(handler, bean.getData().getBooks().get(position).getBid(), viewHolder);
                        p = position;
                    }
                }).start();
            }
        });
        return newView;
    }

    public static class ViewHolder{
        public ImageView image;
        public CircleImageView avatar;
        public TextView status;
        public TextView name;
        public TextView times;
        public TextView deadline;
        public TextView userName;
        public Button rent;

    }

}
