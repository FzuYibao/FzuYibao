package com.maple27.fzuyibao.view.activity;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;

public class AlterPhone extends AppCompatActivity {
    private LoginBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_phone);
        final EditText editText = (EditText) findViewById(R.id.alter_new_phone);
        editText.setText(UserEntity.getPhone());
        Button button =(Button) findViewById(R.id.alter_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newPhone = editText.getText().toString();
                        Log.d("ljm",newPhone);
                        bean = NetworkUtil.AlterPhone(getBaseContext(),newPhone);
                        if(bean == null){
                            Log.d("AlterPhone", "" + bean.getMessage());
                        }else{
                            if (bean.getError_code()==40002){
                                Looper.prepare();
                                Toast.makeText(getBaseContext(),bean.getMessage(),Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            else{
                                UserEntity.setPhone(bean.getData().getUser().getPhone());
                                finish();
                            }

                        }

                    }
                }).start();
            }
        });
    }
}
