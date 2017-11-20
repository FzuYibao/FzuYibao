package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.yanzhenjie.album.Album;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/18.
 */

public class PostActivity extends AppCompatActivity {

    private AppCompatActivity activity;
    private EditText name;
    private EditText info;
    private EditText tag;
    private EditText price;
    private AppCompatSpinner spinner;
    private Button post;
    private Button addPicture;
    private String category;
    private List<String> image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_post);
        init();
    }

    public void init(){
        activity = this;
        name = (EditText) findViewById(R.id.post_name);
        info = (EditText) findViewById(R.id.post_info);
        tag = (EditText) findViewById(R.id.post_tag);
        price = (EditText) findViewById(R.id.post_price);
        spinner = (AppCompatSpinner) findViewById(R.id.post_spinner);
        post = (Button) findViewById(R.id.doPost);
        addPicture = (Button) findViewById(R.id.addPicture_postNew);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.startAlbum(PostActivity.this, 666 , 9);
            }
        });
        /*// 建立数据源
        String[] mItems = getResources().getStringArray(R.array.category);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mItems);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos){
                    case 0:category = "11000";break;
                    case 1:category = "12000";break;
                    case 2:category = "13000";break;
                    case 3:category = "31000";break;
                    case 4:category = "33000";break;
                    case 5:category = "32000";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name2 = name.getText().toString();
                        String info2 = info.getText().toString();
                        String tag2 = tag.getText().toString();
                        String price2 = "¥"+price.getText().toString();
                        NetworkUtil.Post(activity, name2, category, info2, tag2, price2, image);
                    }
                }).start();
                ActivityController.removeActivity(activity);
                activity.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 666){
            if(resultCode == RESULT_OK){
                List<String> s = Album.parseResult(data);
                final String imagePath = s.get(0);
                image = s;
                System.out.println(image + " aaa");
            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
