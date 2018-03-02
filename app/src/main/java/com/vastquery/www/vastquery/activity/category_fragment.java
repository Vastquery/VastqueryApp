package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.DatabaseConnection.GetResources;
import com.vastquery.www.vastquery.PropertyClasses.CategoryDetails;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.GridSpacingItemDecoration;
import com.vastquery.www.vastquery.helper.MyAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class category_fragment extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Context context;
    List<CategoryDetails> details;
    String[] names = {"Agriculture", "Accommodation", "Caterers", "Civil Contractor", "Daily Needs", "Dance & Music",
            "Driving School", "Education & Training", "Electronics", "Emergency", "Fitness", "Hospitals", "Hotels",
            "House keeping", "Jobs consultancy", "Real Estate", "Repairs", "Transporters", "Travels", "Wedding"};

    int[] images = {R.drawable.farmer, R.drawable.accommodation, R.drawable.utensils, R.drawable.construcion,
            R.drawable.furniture, R.drawable.ic_music, R.drawable.drivingschool, R.drawable.trainingcenter,
            R.drawable.computers, R.drawable.ic_favorite, R.drawable.fitnesscentre, R.drawable.hospital,
            R.drawable.hotel, R.drawable.theaters, R.drawable.jobconsultancy, R.drawable.construcion,
            R.drawable.mechanicshop, R.drawable.automobileshop, R.drawable.travel, R.drawable.florist};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_activity, container, false);
        details = new ArrayList<>();
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recyler_view);
        gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        GetResources getResources = new GetResources(context,recyclerView,"G_3");
        getResources.execute();

        return view;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));

    }


}
