package com.vastquery.www.vastquery.helper;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.ReviewClass;
import com.vastquery.www.vastquery.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyHolder> {
    Context context;
    List<ReviewClass> requiredList;

    public ReviewAdapter(Context context, List<ReviewClass> requiredList) {
        this.context = context;
        this.requiredList = requiredList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_review,null);
        ReviewAdapter.MyHolder myHolder = new ReviewAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.user_name.setText(requiredList.get(position).getUser_name());
        holder.user_comment.setText(requiredList.get(position).getUser_review()+"\n\n"+requiredList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return requiredList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView user_name,user_comment;
        View mview;
        public MyHolder(View itemView) {
            super(itemView);

            mview=itemView;
            user_name = itemView.findViewById(R.id.user_name);
            user_comment = itemView.findViewById(R.id.user_review);
        }
    }
}
