package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;

public class ProfessionalActivity extends AppCompatActivity {

    String name,Address;

    TextView phone_number;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Address = intent.getStringExtra("address");
        bitmap = intent.getParcelableExtra("image");
        phone_number = findViewById(R.id.phonenumber);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadbackdrop();
    }
    void loadbackdrop(){
        ImageView prof_image = findViewById(R.id.backdrop_prof);
        prof_image.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
