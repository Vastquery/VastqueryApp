package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vastquery.www.vastquery.R;


public class ImageSliderClass {

    Context context;
    ViewPager viewpager;
    LinearLayout sliderDotsPanel;
    ImageView[] dots;
    int dotscount;

    public ImageSliderClass(Context context, ViewPager viewpager, LinearLayout sliderDotsPanel, int dotscount) {
        this.context = context;
        this.viewpager = viewpager;
        this.sliderDotsPanel = sliderDotsPanel;
        this.dotscount = dotscount;
    }


    public void imageSlide(){
        dots = new ImageView[dotscount];
        for(int i=0; i<dotscount ; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.nonactivedots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(8,0,8,0);

            sliderDotsPanel.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.activedots));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<dotscount ; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.nonactivedots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(),R.drawable.activedots));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
