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

import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.PropertyClasses.EditItems;
import com.vastquery.www.vastquery.R;

import java.util.List;

/**
 * Created by aj-ajay on 4/10/18.
 */

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyHolder>{

    Context context;
    List<EditItems> requiredList;

    public EditAdapter(Context context, List<EditItems> requiredList) {
        this.context = context;
        this.requiredList = requiredList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        EditAdapter.MyHolder myHolder = new EditAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(requiredList.get(position).getName());
        final byte[] decodestring = requiredList.get(position).getImg();
        final Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        holder.recycler_imageview.setImageBitmap(decodebitmap);
        holder.address.setText(requiredList.get(position).getAddress());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
