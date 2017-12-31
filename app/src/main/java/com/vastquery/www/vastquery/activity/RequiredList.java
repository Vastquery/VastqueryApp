package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.MyAdapter;
import com.vastquery.www.vastquery.helper.SimpleStringRecyclerViewAdapter;

public class RequiredList extends AppCompatActivity {

    public static final String extra_name = "";
    Toolbar toolbar;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    String[] names = {"Agriculture","Accommodation","Caterers","Civil Contractor","Daily Needs","Dance & Music",
            "Driving School","Education & Training","Electronics","Emergency","Fitness","Hospitals","Hotels",
            "House keeping","Jobs consultancy","Real Estate","Repairs","Transporters","Travels","Wedding"};

    int[] images = { R.drawable.farmer,R.drawable.accommodation,R.drawable.utensils,R.drawable.construcion,
            R.drawable.furniture,R.drawable.ic_music,R.drawable.drivingschool,R.drawable.trainingcenter,
            R.drawable.computers,R.drawable.ic_favorite,R.drawable.fitnesscentre,R.drawable.hospital,
            R.drawable.hotel,R.drawable.theaters,R.drawable.jobconsultancy,R.drawable.construcion,
            R.drawable.mechanicshop,R.drawable.automobileshop,R.drawable.travel,R.drawable.florist};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_list);

        // getting the string using intent
        Intent intent = getIntent();
        String text = intent.getStringExtra(extra_name);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview_requiredlist);
        linearLayoutManager = new LinearLayoutManager(RequiredList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        SimpleStringRecyclerViewAdapter myAdapter = new SimpleStringRecyclerViewAdapter(RequiredList.this, names, images);
        recyclerView.setAdapter(myAdapter);

    }
}
