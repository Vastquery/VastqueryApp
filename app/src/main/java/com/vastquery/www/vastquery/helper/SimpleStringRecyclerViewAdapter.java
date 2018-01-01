package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.ClassListItems;

import java.util.List;

public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.MyHolder> {

    Context context;
    List<ClassListItems> requiredList;

    public SimpleStringRecyclerViewAdapter(Context context,List<ClassListItems> requiredList ) {
        this.context = context;
        this.requiredList = requiredList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        SimpleStringRecyclerViewAdapter.MyHolder myHolder = new SimpleStringRecyclerViewAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(requiredList.get(position).getName()+"");
        byte[] decodestring = requiredList.get(position).getImg();
        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        holder.recycler_imageview.setImageBitmap(decodebitmap);
    }


    @Override
    public int getItemCount() {
        return requiredList.size();
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
