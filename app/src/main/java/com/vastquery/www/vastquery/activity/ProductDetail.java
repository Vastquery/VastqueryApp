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
    List<byte[]> photos,temp;
    private int dotscount;
    byte[] front;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();

        photos = new ArrayList<>();
        temp = new ArrayList<>();
        id = intent.getIntExtra("id",0);
        front = intent.getByteArrayExtra("front");


        SyncData_Photos syncData_photos = new SyncData_Photos();
        syncData_photos.execute();



    }


    public class SyncData_Photos extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess=false;
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "select Back_View,Side_View from tblProduct where P_ID="+id;
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                        photos.add(rs.getBytes("Back_view"));
                        photos.add(rs.getBytes("Side_view"));
                    }
                    isSuccess = true;
                    ConnectionResult = "successful";
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
                if(isSuccess){
                    photos.add(front);
                    viewpager = findViewById(R.id.photo_viewpager);
                    PhotoAdapter photoAdapter = new PhotoAdapter(ProductDetail.this,photos);
                    viewpager.setAdapter(photoAdapter);

                    sliderDotsPanel =  findViewById(R.id.dots);
                    dotscount = photoAdapter.getCount();

                    ImageSliderClass imageSliderClass = new ImageSliderClass(ProductDetail.this,viewpager,sliderDotsPanel,dotscount);
                    imageSliderClass.imageSlide();


                }
                else Toast.makeText(ProductDetail.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
