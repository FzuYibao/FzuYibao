package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maple27.fzuyibao.R;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.sss);
        ImageView imageView = (ImageView) findViewById(R.id.Collect_YesOrNot);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
