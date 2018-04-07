package com.vastquery.www.vastquery.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vastquery.www.vastquery.PropertyClasses.FacilityClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.activity.ServiceList;

import java.util.List;



public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.MyHolder> {

    Context context;
    List<FacilityClass> facilities;

    public FacilityAdapter(Context context, List<FacilityClass> facilities) {
        this.context = context;
        this.facilities = facilities;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, null);
        FacilityAdapter.MyHolder facilityAdapter = new FacilityAdapter.MyHolder(layout);
        return facilityAdapter;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final FacilityClass facility = facilities.get(position);
        holder.name.setText(facility.getFacilities_desc());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ServiceList.class);
                intent.putExtra("facility",facility.getFaciliti_Id());
                intent.putExtra("catId",facility.getCat_Id());
                intent.putExtra("facilitydesc",facility.getFacilities_desc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return facilities.size();
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
