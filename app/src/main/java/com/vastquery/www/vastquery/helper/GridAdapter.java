package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;


public class GridAdapter extends BaseAdapter {

    Context context;
    private final String[] values;
    private final int[] images;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] values, int[] images) {
        this.context = context;
        this.images = images;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null ){

            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item,null);
            ImageView imageView = view.findViewById(R.id.grid_imageview);
            TextView textView = view.findViewById(R.id.grid_textview);
            imageView.setImageResource(images[i]);
            textView.setText(values[i]);
        }
        return view;
    }
}
