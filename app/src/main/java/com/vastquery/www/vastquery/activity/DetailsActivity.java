package com.vastquery.www.vastquery.activity;

import android.content.Intent;
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
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import com.vastquery.www.vastquery.R;





public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final int EXTRA_ID = 0;
    public  int shop_id;
    public byte[] bytes;
    public String address;

    private SampleAdapter sampleAdapter;
    private  FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        final String name = intent.getStringExtra(EXTRA_NAME);
        shop_id = intent.getIntExtra(String.valueOf(EXTRA_ID),0);
        address = intent.getStringExtra("address");
        bytes = intent.getByteArrayExtra("BitmapImage");
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar_details);
        collapsingToolbar.setTitle(name);


        ViewPager viewPager = findViewById(R.id.viewpager_details);
        if(viewPager != null) {
            sampleAdapter = new SampleAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sampleAdapter);
        }



        TabLayout tabLayout =  findViewById(R.id.tabs_details);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        loadBackdrop();


    }

    private void loadBackdrop() {
        final ImageView imageView =  findViewById(R.id.backdrop);
        Glide.with(this).load(bytes).asBitmap().centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                    data.putInt("data",shop_id);
                    data.putString("address",address);
                    reviewActivity.setArguments(data);
                    fragmentManager.beginTransaction();
                    return reviewActivity;
                case 1:
                    ProductActivity productActivity = new ProductActivity();
                    Bundle datashop = new Bundle();
                    datashop.putInt("data",shop_id);
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


}
