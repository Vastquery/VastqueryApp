package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.vastquery.www.vastquery.R;

public class PostAdd extends AppCompatActivity  implements View.OnClickListener{

    Button shop_post,prof_post,shop_edit,prof_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        shop_post = findViewById(R.id.shop_post);
        prof_post = findViewById(R.id.prof_post);
        shop_edit = findViewById(R.id.shop_edit);
        prof_edit = findViewById(R.id.prof_edit);

        shop_post.setOnClickListener(this);
        shop_edit.setOnClickListener(this);
        prof_post.setOnClickListener(this);
        prof_edit.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shop_post:
                startActivity(new Intent(PostAdd.this,postShopForm.class));
                break;
            case R.id.shop_edit:
                break;
            case R.id.prof_edit:
                break;
            case R.id.prof_post:
                startActivity(new Intent (PostAdd.this,postProfForm.class));
                break;
        }
    }
}
