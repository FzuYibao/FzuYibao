package com.maple27.fzuyibao.view.activity;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;

public class AlterNickname extends AppCompatActivity {
    private LoginBean bean;
    private ImageView delete_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_nickname);
        final EditText editText = (EditText) findViewById(R.id.altet_new_nickname);
        editText.setText(UserEntity.getNickname());
        Button button =(Button) findViewById(R.id.alter_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newNickname = editText.getText().toString();
                        bean = NetworkUtil.AlterNickname(getBaseContext(),newNickname);
                        if(bean.getError_code()==40002){
                            Looper.prepare();
                            Toast.makeText(getBaseContext(),bean.getMessage(),Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else {
                            UserEntity.setNickname(bean.getData().getUser().getNickname());
                            finish();
                        }
                    }
                }).start();
            }
        });
    }
}
