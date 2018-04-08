package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.UsersOfShop;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyHolder>{

    Context context;
    List<ClassListItems> requiredList;
    boolean decision;

    public ChatListAdapter(Context context, List<ClassListItems> requiredList,boolean decision) {
        this.context = context;
        this.requiredList = requiredList;
        this.decision = decision;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        ChatListAdapter.MyHolder chatListAdapter = new ChatListAdapter.MyHolder(layout);
        return chatListAdapter;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.textView.setText(requiredList.get(position).getName());
        final byte[] decodestring = requiredList.get(position).getImg();
        final Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        holder.recycler_imageview.setImageBitmap(decodebitmap);
        holder.address.setText(requiredList.get(position).getAddress());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(decision){

                }else{
                    Intent intent = new Intent(context, UsersOfShop.class);
                    intent.putExtra("cat_id",requiredList.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return requiredList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView recycler_imageview;
        TextView textView,address;
        View mview;

        public MyHolder(View itemView) {
            super(itemView);

            mview=itemView;
            recycler_imageview = itemView.findViewById(R.id.avatar_list);
            textView = itemView.findViewById(R.id.text_list);
            address = itemView.findViewById(R.id.address_list);

        }
    }
}
