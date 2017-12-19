package com.vastquery.www.vastquery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vastquery.www.vastquery.R;

/**
 * Created by aj-ajay on 12/16/17.
 */

public class shopwise_fragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.shopwise_activity, container, false);
        return rv;

    }
}
