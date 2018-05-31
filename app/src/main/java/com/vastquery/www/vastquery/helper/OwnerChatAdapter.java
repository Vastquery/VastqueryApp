package com.vastquery.www.vastquery.helper;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.vastquery.www.vastquery.DatabaseConnection.UpdateOwnerMessage;
import com.vastquery.www.vastquery.PropertyClasses.ChatDetails;
import com.vastquery.www.vastquery.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aj-ajay on 4/9/18.
 */

public class OwnerChatAdapter extends RecyclerView.Adapter<OwnerChatAdapter.MyHolder> {

    Context context;
    List<ChatDetails> details;

    public OwnerChatAdapter(Context context, List<ChatDetails> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_owner_chat,null);
        OwnerChatAdapter.MyHolder myHolder = new OwnerChatAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final ChatDetails detail = details.get(position);
        final String[] message = new String[1];

        holder.out.setText(detail.getMessage());
        if(detail.getStatus().equals("R")){
            holder.in.setText(detail.getReply());
        }else{
            holder.in.setText("Click here to reply");
            holder.in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context,R.style.FullHeightDialog);
                    dialog.setContentView(R.layout.activity_send);
                    dialog.setCancelable(true);


                    final AutoCompleteTextView sendtext = dialog.findViewById(R.id.sendtext);


                    final Button send = dialog.findViewById(R.id.send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            message[0] = sendtext.getText().toString().trim();
                            UpdateOwnerMessage ownerMessage = new UpdateOwnerMessage(context,detail.getId(), message[0],holder.in);
                            ownerMessage.execute();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
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
