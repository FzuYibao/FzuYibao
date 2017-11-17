package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

import org.w3c.dom.Text;

public class MyMessage extends AppCompatActivity {

    private TextView nickname;
    private TextView username;
    private TextView major;
    private TextView grade;
    private TextView phone;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        phone.setText(UserEntity.getPhone());
        nickname.setText(UserEntity.getNickname());
    }

    public void init(){
        avatar = (CircleImageView) findViewById(R.id.my_message_avatar) ;
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.displayImage(this, UserEntity.getAvatar_path(), avatar);
        nickname = (TextView) findViewById(R.id.my_message_nickname);
        username = (TextView) findViewById(R.id.my_message_username);
        major = (TextView) findViewById(R.id.my_message_major);
        grade = (TextView) findViewById(R.id.my_message_grade);
        phone = (TextView) findViewById(R.id.my_message_phone);
        nickname.setText(UserEntity.getNickname());
        username.setText(UserEntity.getName());
        major.setText(UserEntity.getMajor());
        grade.setText(UserEntity.getGrade());
        phone.setText(UserEntity.getPhone());
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMessage.this,AlterNickname.class);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMessage.this,AlterPhone.class);
                startActivity(intent);
            }
        });

    }
}
