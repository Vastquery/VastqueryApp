package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vastquery.www.vastquery.PropertyClasses.CategoryDetails;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.RequiredList;
import com.vastquery.www.vastquery.activity.ServiceActivity;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context context;
    List<CategoryDetails> details;


    public MyAdapter(Context context, List<CategoryDetails> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,null);
        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final CategoryDetails detail = details.get(position);
        Glide.with(context).load(detail.getImage()).asBitmap().centerCrop().into(holder.recycler_imageview);
        holder.textView.setText(detail.getName());

        holder.recycler_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent;
                if(detail.getGroupid().equals("G_3")){
                    intent = new Intent(context, ServiceActivity.class);
                    intent.putExtra("cat_id", detail.getCatId());
                }else {
                    intent = new Intent(context, RequiredList.class);
                    intent.putExtra(RequiredList.extra_name, detail.getCatId());
                }
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return details.size();
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
