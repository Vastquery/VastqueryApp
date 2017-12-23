package com.vastquery.www.vastquery.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vastquery.www.vastquery.R;

public class PostAdds extends AppCompatActivity {

    private MyAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_adds);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);

        //tabbedLayout
        ViewPager viewpager = findViewById(R.id.viewpager_post);
        if (viewpager != null) {
            myadapter = new MyAdapter(getSupportFragmentManager());
            viewpager.setAdapter(myadapter);
        }
        TabLayout tabLayout = findViewById(R.id.tabs_post);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    //Tabbed Layout
    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PostFragmentShop postFragment = new PostFragmentShop();
                    return postFragment;
                case 1:
                    PostFragmentProfessional post_fragmentprofession = new PostFragmentProfessional();
                    return post_fragmentprofession;
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
