package com.maple27.fzuyibao.view.activity;

import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;

public class AlterNicknameActivity extends AppCompatActivity {
    private LoginBean bean;
    private EditText editText ;
    private AppCompatActivity activity;
    private ImageView delete;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alter_information,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_nickname);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.finish();
                break;
            case (R.id.alter_commit):
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newNickname = editText.getText().toString();
                        bean = NetworkUtil.AlterNickname(getBaseContext(), newNickname);
                        if (bean.getError_code() == 40002) {
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            UserEntity.setNickname(bean.getData().getUser().getNickname());
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), "修改昵称成功", Toast.LENGTH_SHORT).show();
                            activity.finish();
                            Looper.loop();

                        }
                    }
                }).start();
                break;
            default:
        }
        return true;
        }


    public void init(){
        activity = this;
        delete = (ImageView) findViewById(R.id.delete_nickname);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.alter_name_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
            //here 加返回键图片
        }
        editText=(EditText)findViewById(R.id.alter_new_nickname);
        editText.setText(UserEntity.getNickname());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        StatusBarUtil.setStatusBar(this);
    }
}
