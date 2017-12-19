package com.vastquery.www.vastquery.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.vastquery.www.vastquery.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //sliding image
    private ViewPager viewpager;
    LinearLayout sliderDotsPanel;
    private int dotscount;
    private ImageView[] dots;


    //toolbar
    private Toolbar toolbar;

    //tabbed layout
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*for keyboard and background image
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/


        // tooolbar
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);



        //Tabbeb activities
        ViewPager viewPager = findViewById(R.id.viewpager);
        if(viewPager != null) {
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        //Bottom Bar
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_navigation, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {

            }
            @Override
            public void onMenuTabReSelected(int menuItemId) {

            }
        });

        //Image slider
        SlidingImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    //Tabbed Layout
    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    shopwise_fragment shopwiseFragment = new shopwise_fragment();
                    return shopwiseFragment;
                case 1:
                    professionalwise_fragment professionalwiseFragment = new professionalwise_fragment();
                    return professionalwiseFragment;
                case 2:
                    events_fragment eventsFragment = new events_fragment();
                    return eventsFragment;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

    //sliding image
    public void SlidingImage(){

        // sliding viewpager
        viewpager = findViewById(R.id.sliding_viewpager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewpager.setAdapter(viewPageAdapter);

        //auto slider
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        //dots in slider
        sliderDotsPanel =  findViewById(R.id.dots);
        dotscount = viewPageAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i=0; i<dotscount ; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactivedots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(8,0,8,0);

            sliderDotsPanel.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedots));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<dotscount ; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactivedots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activedots));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewpager.getCurrentItem() == 0)
                        viewpager.setCurrentItem(1);
                    else if(viewpager.getCurrentItem() == 1)
                        viewpager.setCurrentItem(2);
                    else
                        viewpager.setCurrentItem(0);
                }
            });
        }
    }


}
