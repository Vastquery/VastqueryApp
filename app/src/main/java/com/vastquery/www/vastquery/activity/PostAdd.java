package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.vastquery.www.vastquery.R;

public class PostAdd extends Fragment implements View.OnClickListener{

    Button shop_post,prof_post,shop_edit,prof_edit;
    Context context;


    public static PostAdd newInstance(String param1, String param2) {
        PostAdd fragment = new PostAdd();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post_add, container, false);
        context = view.getContext();
        shop_post = view.findViewById(R.id.shop_post);
        prof_post = view.findViewById(R.id.prof_post);
        shop_edit = view.findViewById(R.id.shop_edit);
        prof_edit = view.findViewById(R.id.prof_edit);
        shop_post.setOnClickListener(this);
        shop_edit.setOnClickListener(this);
        prof_post.setOnClickListener(this);
        prof_edit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shop_post:
                Intent intent = new Intent(context, postShopForm.class);
                context.startActivity(intent);
                break;
            case R.id.shop_edit:
                break;
            case R.id.prof_edit:
                break;
            case R.id.prof_post:
                Intent intent4 = new Intent(context, postProfForm.class);
                context.startActivity(intent4);
                break;
        }
    }

}
