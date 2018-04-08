package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.UserDetails;
import com.vastquery.www.vastquery.R;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyHolder> {

    Context context;
    List<UserDetails> userList;

    public UserListAdapter(Context context, List<UserDetails> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_review,null);
        UserListAdapter.MyHolder userListAdapter = new UserListAdapter.MyHolder(layout);

        return userListAdapter;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        UserDetails details = userList.get(position);
        holder.user_name.setText(details.getName());
        holder.mobile.setText(details.getMobile());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder{


        TextView user_name,mobile;
        View mview;

        public MyHolder(View itemView) {
            super(itemView);

            mview=itemView;

            user_name = itemView.findViewById(R.id.user_name);
            mobile = itemView.findViewById(R.id.mobile);

        }
    }
}
