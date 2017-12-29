package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;

public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.MyHolder> {

    Context context;
    private String[] names;
    private int[] images;

    public SimpleStringRecyclerViewAdapter(Context context, String[] names, int[] images) {
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        SimpleStringRecyclerViewAdapter.MyHolder myHolder = new SimpleStringRecyclerViewAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.recycler_imageview.setImageResource(images[position]);
        holder.textView.setText(names[position]);
    }


    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView recycler_imageview;
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);

            recycler_imageview = itemView.findViewById(R.id.avatar_list);
            textView = itemView.findViewById(R.id.text_list);

        }
    }
}
