package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.MyAdapter;


public class category_fragment extends Fragment{

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;


    String[] names = {"Agriculture","Accommodation","Caterers","Civil Contractor","Daily Needs","Dance & Music",
            "Driving School","Education & Training","Electronics","Emergency","Fitness","Hospitals","Hotels",
            "House keeping","Jobs","Real Estate","Repairs","Transporters","Training center","Travels","Wedding"};

    int[] images = { R.drawable.farmer,R.drawable.accommodation,R.drawable.utensils,R.drawable.construcion,
                    R.drawable.furniture,R.drawable.ic_music,R.drawable.drivingschool,R.drawable.trainingcenter,
                    R.drawable.computers,R.drawable.ic_favorite,R.drawable.fitnesscentre,R.drawable.hospital,
                    R.drawable.hotel,R.drawable.theaters,R.drawable.jobconsultancy,R.drawable.construcion,
                    R.drawable.mechanicshop,R.drawable.automobileshop,R.drawable.travel,R.drawable.florist};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_activity, container, false);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyler_view);
        gridLayoutManager = new GridLayoutManager(context,3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        MyAdapter myAdapter = new MyAdapter(context,names,images);
        recyclerView.setAdapter(myAdapter);

        return view;
    }


}
