package com.vastquery.www.vastquery.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Fragment {


    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;



    //sliding image
    private ViewPager viewpager;
    LinearLayout sliderDotsPanel;
    private int dotscount;
    private ImageView[] dots;

    Context context;

    private FragmentActivity myContext;


    //toolbar


    //tabbed layout

    public static MainActivity newInstance(String param1, String param2) {
        MainActivity fragment = new MainActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        //connectivity check

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_main,container,false);
        context = view.getContext();


        toolbar =  view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*for keyboard and background image
        getWindow().setBackgroundDrawableResource(R.drawable.background);*/
        // tooolbar
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Navigation View
        //Tabbeb activities

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        /*if(viewPager != null) {
            sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
        }*/

        TabLayout tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        //Image slider
        SlidingImage(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample_actions, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.filter:
                intent = new Intent(context, postShopForm.class);
                context.startActivity(intent);
                break;
            case R.id.action_search:
                intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.nav_changenumber:
                                toast("change number yet to be created");
                                break;
                            case R.id.nav_affiliatedwork:
                                toast("affiliatedwork yet to be created");
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new category_fragment(), "ServiceProvider");
        adapter.addFragment(new shopwise_fragment(), "Shop");
        adapter.addFragment(new professionalwise_fragment(), "Professionals");
        viewPager.setAdapter(adapter);



    }

    //Tabbed Layout
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    //sliding image
    public void SlidingImage(View view){

        // sliding viewpager
        viewpager = view.findViewById(R.id.sliding_viewpager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(context);
        viewpager.setAdapter(viewPageAdapter);

        //auto slider
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        //dots in slider
        sliderDotsPanel =  view.findViewById(R.id.dots);
        dotscount = viewPageAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i=0; i<dotscount ; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(myContext.getApplicationContext(),R.drawable.nonactivedots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(8,0,8,0);

            sliderDotsPanel.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(myContext.getApplicationContext(), R.drawable.activedots));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<dotscount ; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(myContext.getApplicationContext(), R.drawable.nonactivedots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(myContext.getApplicationContext(),R.drawable.activedots));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            myContext.runOnUiThread(new Runnable() {
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

    // toast something
    public void toast(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
