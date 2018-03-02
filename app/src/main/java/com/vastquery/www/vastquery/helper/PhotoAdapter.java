package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vastquery.www.vastquery.R;

import java.util.List;

import static com.vastquery.www.vastquery.activity.ReviewActivity.getBitmap;


public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<byte[]> images;

    public PhotoAdapter(Context context,List<byte[]> images) {
        this.context = context;
        this.images = images;
    }



    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout,null);

        ImageView imageView = view.findViewById(R.id.imageView);
        if(images.get(position)!=null)
            Glide.with(context).load(images.get(position)).asBitmap().centerCrop().into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager)container;
        View view = (View) object;
        vp.removeView(view);
    }
}
