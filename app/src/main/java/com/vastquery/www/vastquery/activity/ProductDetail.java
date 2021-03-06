package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ProductClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ImageSliderClass;
import com.vastquery.www.vastquery.helper.PhotoAdapter;
import com.vastquery.www.vastquery.helper.ProductAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.vastquery.www.vastquery.activity.ReviewActivity.getBitmap;

public class ProductDetail extends AppCompatActivity {

    ViewPager viewpager;
    LinearLayout sliderDotsPanel;
    List<byte[]> photos;
    private int dotscount;
    byte[] front,back,side;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();

        photos = new ArrayList<>();
        id = intent.getStringExtra("id");
        front = intent.getByteArrayExtra("front");
        back = intent.getByteArrayExtra("back");
        side = intent.getByteArrayExtra("side");
        photos.add(front);
        photos.add(back);
        photos.add(side);
        sliderDotsPanel =  findViewById(R.id.dots);


        viewpager = findViewById(R.id.photo_viewpager);
        PhotoAdapter photoAdapter = new PhotoAdapter(ProductDetail.this,photos);
        viewpager.setAdapter(photoAdapter);


        dotscount = photos.size();

        ImageSliderClass imageSliderClass = new ImageSliderClass(ProductDetail.this,viewpager,sliderDotsPanel,dotscount);
        imageSliderClass.imageSlide();



    }


}
