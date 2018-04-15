package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.ChatDetails;
import com.vastquery.www.vastquery.R;

import java.util.List;


public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.MyHolder> {

    Context context;
    List<ChatDetails> details;

    public ChatMessagesAdapter(Context context, List<ChatDetails> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_single_item,null);
        ChatMessagesAdapter.MyHolder myHolder = new ChatMessagesAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ChatDetails detail = details.get(position);
        holder.in.setText(detail.getMessage());
        if(detail.getStatus().equals("Y")){
            holder.out.setText(detail.getReply());
        }else{
            holder.out.setText("Thanks for the Interest we'll reply soon");
        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView in,out;
        public MyHolder(View itemView) {
            super(itemView);
            in = itemView.findViewById(R.id.in);
            out = itemView.findViewById(R.id.out);
        }
    }
}
