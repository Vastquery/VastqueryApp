package com.vastquery.www.vastquery.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.ProductClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.DetailsActivity;

import java.util.List;

import static com.vastquery.www.vastquery.activity.ReviewActivity.getBitmap;

/**
 * Created by aj-ajay on 1/13/18.
 */

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {


    Context context;
    List<ProductClass> products;

    public WishlistAdapter(Context context, List<ProductClass> products) {
        this.context = context;
        this.products = products;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wishlist,null);
        WishlistAdapter.MyHolder myHolder = new WishlistAdapter.MyHolder(layout);
        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final ProductClass product = products.get(position);
        holder.product_name.setText(product.getProduct_name());
        holder.product_price.setText(""+product.getPrice());
        holder.avatar_list.setImageBitmap(getBitmap(product.getFront()));
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(String.valueOf(DetailsActivity.EXTRA_ID),product.getShop_Id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        View mview;
        ImageView avatar_list;
        TextView product_name,product_price;

        public MyHolder(View itemView) {
            super(itemView);

            mview=itemView;
            avatar_list = itemView.findViewById(R.id.avatar_list);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
        }
    }
}
