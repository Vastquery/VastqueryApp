package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.content.res.Resources;
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
import android.widget.RelativeLayout;

import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.GridSpacingItemDecoration;
import com.vastquery.www.vastquery.helper.MyAdapter;


public class professionalwise_fragment extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;


    String[] names = {"Accountant","Adminstrator","Analyst","Architect","Archivist","Artist","Author",
                        "Baker","Barber","Bartender","Beautician","Broadcaster","Bookkeeper","Bricklayer",
                        "Care worker","Carpenter","Chef","Cleaner","Counselor","Decorater",
                        "Doctor","Driver","Electrician","Engineer","Enterpreneur","Farmer",
                        "Firefighter","Florist","Instructor","Journalist","Labourer",
                        "Lawyer","Librarian","Mechanic","Masseur","Musician",
                        "Nurse","Painter","Photographer","Plumber","Police","Politician","Scientist",
                        "Social worker","Stock broker","Vocalist","Water supplier"};

    int images[] = { R.drawable.accountant,R.drawable.administrator,R.drawable.analyst,R.drawable.architech,
                    R.drawable.architech,R.drawable.archivist,R.drawable.artist,R.drawable.author,
                    R.drawable.baker,R.drawable.barber,R.drawable.bartender,R.drawable.beautician,
                    R.drawable.broadcaster,R.drawable.bookkeeper,R.drawable.dricklayer,R.drawable.careworker,
                    R.drawable.carpenter,R.drawable.chef,R.drawable.cleaner,R.drawable.counselor,
                    R.drawable.decorator,R.drawable.doctor,R.drawable.driver,R.drawable.electrician,
                    R.drawable.engineer,R.drawable.entrepreneur,R.drawable.farmer,R.drawable.firefighter,
                    R.drawable.florist,R.drawable.instructor,R.drawable.journalist,R.drawable.labourer,
                    R.drawable.lawyer,R.drawable.librarian,R.drawable.mechanic,R.drawable.masseur,
                    R.drawable.musician,R.drawable.nurse,R.drawable.painter,R.drawable.photographer,
                    R.drawable.plumber,R.drawable.police,R.drawable.politician,R.drawable.scientist,
                    R.drawable.socialworker,R.drawable.stockbroker,R.drawable.vocalist,R.drawable.watersupplyer
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.category_activity, container,false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyler_view);
        gridLayoutManager = new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        MyAdapter myAdapter = new MyAdapter(context,names,images);
        recyclerView.setAdapter(myAdapter);

        return view;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
