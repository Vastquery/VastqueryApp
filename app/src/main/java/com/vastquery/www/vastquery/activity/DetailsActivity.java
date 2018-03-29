package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ProductClass;
import com.vastquery.www.vastquery.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "";
    public  String shop_id;
    public byte[] bytes;
    public String address,name;

    private SampleAdapter sampleAdapter;
    private  FragmentManager fragmentManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        shop_id = intent.getStringExtra(String.valueOf(EXTRA_ID));

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        SyncData_details syncDataDetails = new SyncData_details();
        syncDataDetails.execute();


        ViewPager viewPager = findViewById(R.id.viewpager_details);
        if(viewPager != null) {
            sampleAdapter = new SampleAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sampleAdapter);
        }



        TabLayout tabLayout =  findViewById(R.id.tabs_details);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


    }

    private void loadBackdrop() {
        final ImageView imageView =  findViewById(R.id.backdrop);
        Glide.with(this).load(bytes).asBitmap().centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SampleAdapter extends FragmentPagerAdapter {

        public SampleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    ReviewActivity reviewActivity = new ReviewActivity();
                    Bundle data = new Bundle();
                    data.putString("data",shop_id);
                    data.putString("address",address);
                    reviewActivity.setArguments(data);
                    fragmentManager.beginTransaction();
                    return reviewActivity;
                case 1:
                    ProductActivity productActivity = new ProductActivity();
                    Bundle datashop = new Bundle();
                    datashop.putString("data",shop_id);
                    productActivity.setArguments(datashop);
                    fragmentManager.beginTransaction();
                    return productActivity;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class SyncData_details extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess=false,hasProduct=false;

        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(DetailsActivity.this,"","Loading...",true);

            Toast.makeText(DetailsActivity.this,"Wait data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "select SubCategory_Name,SubCategory_Address,SubCategory_Logo from tblSubCategory where SubCategory_Id='"+shop_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                        hasProduct = true;
                        do{
                            name = rs.getString("SubCategory_Name");
                            address = rs.getString("SubCategory_Address");
                            bytes = rs.getBytes("SubCategory_Logo");
                        }while (rs.next());
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
            dialog.dismiss();
            if(isSuccess){
                CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar_details);
                collapsingToolbar.setTitle(name);
                loadBackdrop();
            }
            else Toast.makeText(DetailsActivity.this,s,Toast.LENGTH_LONG).show();
        }
    }


}
