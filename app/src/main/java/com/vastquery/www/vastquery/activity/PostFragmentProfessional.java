package com.vastquery.www.vastquery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;

/**
 * Created by aj-ajay on 12/22/17.
 */

public class PostFragmentProfessional extends Fragment {
    private static TextView post,edit;
    private static ImageView post_image,edit_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postadd_fragment, container, false);

        post =  view.findViewById(R.id.post_textview);
        edit =  view.findViewById(R.id.edit_textview);
        post_image = view.findViewById(R.id.post_ads);
        edit_image = view.findViewById(R.id.edit_ads);

        post.setText("Post Profession");
        edit.setText("Edit Profession");

        return view;
    }
}
