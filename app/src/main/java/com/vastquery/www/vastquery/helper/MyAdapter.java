package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.RequiredList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context context;
    String[] names;
    int[] images;


    public MyAdapter(Context context, String[] names, int[] images) {
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,null);
        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.recycler_imageview.setImageResource(images[position]);
        holder.textView.setText(names[position]);

        holder.recycler_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent  intent = new Intent(context, RequiredList.class);
                intent.putExtra(RequiredList.extra_name,holder.textView.getText().toString());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageButton recycler_imageview;
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);

            recycler_imageview = itemView.findViewById(R.id.grid_imageview);
            textView = itemView.findViewById(R.id.grid_textview);

        }
    }
}
