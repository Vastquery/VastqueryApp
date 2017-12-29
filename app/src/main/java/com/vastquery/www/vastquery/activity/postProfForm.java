package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.vastquery.www.vastquery.R;

import java.util.List;

public class postProfForm extends AppCompatActivity implements View.OnClickListener {

    private static AutoCompleteTextView prof_name,prof_address,prof_subtye,prof_city,prof_pincode,prof_phone,prof_mobile1,prof_mobile2,prof_website;
    private static Spinner prof_type,prof_state,prof_district;
    private static Button prof_clear;

    String[] names = {"-please select the professional type","Accountant","Adminstrator","Analyst","Architect","Archivist","Artist","Author",
            "Baker","Barber","Bartender","Beautician","Broadcaster","Bookkeeper","Bricklayer",
            "Care worker","Carpenter","Chef","Cleaner","Counselor","Decorater",
            "Doctor","Driver","Electrician","Engineer","Enterpreneur","Farmer",
            "Firefighter","Florist","Instructor","Journalist","Labourer",
            "Lawyer","Librarian","Mechanic","Masseur","Musician",
            "Nurse","Painter","Photographer","Plumber","Police","Politician","Scientist",
            "Social worker","Stock broker","Vocalist","Water supplier"};


    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_prof_form);

        prof_clear = findViewById(R.id.prof_clear);
        prof_name = findViewById(R.id.prof_name);
        prof_type = findViewById(R.id.prof_type);
        prof_state = findViewById(R.id.prof_state);
        prof_district = findViewById(R.id.prof_district);
        prof_address = findViewById(R.id.prof_address);
        prof_city = findViewById(R.id.prof_city);
        prof_pincode = findViewById(R.id.prof_pincode);
        prof_phone = findViewById(R.id.prof_phone);
        prof_mobile1 = findViewById(R.id.prof_mobile1);
        prof_mobile2 = findViewById(R.id.prof_mobile2);
        prof_website = findViewById(R.id.prof_website);
        prof_subtye = findViewById(R.id.prof_subtye);

        ArrayAdapter<String> shopTypeAdapter = new ArrayAdapter<String>(postProfForm.this,
                android.R.layout.simple_list_item_1,names);
        shopTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prof_type.setAdapter(shopTypeAdapter);

        postShopForm.setDistrictState(postProfForm.this,prof_state,prof_district);

        //Button action
        prof_clear.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.prof_submit:
                break;
            case R.id.prof_clear:
                clearaboveData();
                break;
        }
    }

    //clear button
    private void clearaboveData(){
        prof_name.setText("");
        prof_type.setSelection(0);
        prof_state.setSelection(0);
        prof_address.setText("");
        prof_city.setText("");
        prof_pincode.setText("");
        prof_phone.setText("");
        prof_mobile1.setText("");
        prof_mobile2.setText("");
        prof_website.setText("");
    }



}
