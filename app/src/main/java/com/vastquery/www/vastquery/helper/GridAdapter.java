package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.SearchClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.DetailsActivity;
import com.vastquery.www.vastquery.activity.ProfessionalActivity;

import java.util.List;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyHolder> {

    Context context;
    List<SearchClass> values;

    public GridAdapter(Context context, List<SearchClass> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,null);
        GridAdapter.MyHolder gridAdapter = new GridAdapter.MyHolder(layout);
        return gridAdapter;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final SearchClass value = values.get(position);
        holder.name.setText(value.getName());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if(value.getGroup_id().equals("G_1") || value.getGroup_id().equals("G_3")){
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(String.valueOf(DetailsActivity.EXTRA_ID),value.getSub_cateId());
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, ProfessionalActivity.class);
                    intent.putExtra("id",value.getSub_cateId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        View mview;
        TextView name;

        public MyHolder(View itemView) {
            super(itemView);
            mview=itemView;
            name = itemView.findViewById(R.id.textview);
        }
    }
}
