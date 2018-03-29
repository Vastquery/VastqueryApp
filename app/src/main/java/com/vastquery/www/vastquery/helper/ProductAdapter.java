package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.PropertyClasses.ProductClass;
import com.vastquery.www.vastquery.activity.ProductActivity;
import com.vastquery.www.vastquery.activity.ProductDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    List<ProductClass> products;

    public ProductAdapter(Context context, List<ProductClass> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product,null);
        ProductAdapter.MyHolder productAdapter = new ProductAdapter.MyHolder(layout);
        return productAdapter;

    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final ProductClass product = products.get(position);
        context = holder.mview.getContext();
        holder.product_name.setText(product.getProduct_name());
        holder.product_price.setText(""+product.getPrice());
        byte[] decodestring = product.getFront();
        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        holder.product_imageview.setImageBitmap(decodebitmap);
        holder.heart_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(holder.mview.getContext(),"liked",Toast.LENGTH_LONG).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(holder.mview.getContext(),"unliked",Toast.LENGTH_LONG).show();
            }
        });
        holder.product_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("id",product.getId());
                intent.putExtra("front",product.getFront());
                intent.putExtra("back",product.getFront());
                intent.putExtra("side",product.getFront());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView product_imageview;
        TextView product_name,product_price;
        View mview;
        LikeButton heart_button;
        public MyHolder(View itemView) {
            super(itemView);
            mview=itemView;
            product_imageview = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            heart_button = itemView.findViewById(R.id.heart_button);
        }
    }
}
